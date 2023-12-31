package com.passion.teampassiontrelloproject.card.service;

import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.card.dto.CardResponseDto;
import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.card.repository.CardRepository;
import com.passion.teampassiontrelloproject.cardCollaborators.dto.CardCollaboratorsRequestDto;
import com.passion.teampassiontrelloproject.cardCollaborators.dto.CardCollaboratorsResponseDto;
import com.passion.teampassiontrelloproject.cardCollaborators.entity.CardCollaborators;
import com.passion.teampassiontrelloproject.cardCollaborators.repository.CardCollaboratorRepository;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.column.repository.ColumnsRepository;
import com.passion.teampassiontrelloproject.common.advice.custom.CardNotFoundException;
import com.passion.teampassiontrelloproject.common.advice.custom.ColumnNotFoundException;
import com.passion.teampassiontrelloproject.common.advice.custom.SelfAssignmentNotAllowedException;
import com.passion.teampassiontrelloproject.common.advice.custom.UserNotFoundException;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.slacknotify.SlackService;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.repository.UserRepository;
import com.passion.teampassiontrelloproject.userBoard.repository.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardCollaboratorRepository cardCollaboratorRepository;
    private final ColumnsRepository columnsRepository;
    private final SlackService slackService;

    @Transactional
    public CardResponseDto getCardById(Long id) {
        Card card = findCard(id);
        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public CardResponseDto createdCard(CardRequestDto cardRequestDto, Long boardId, Long columnId, User user) {
        Columns columns = columnsRepository.findById(columnId)
                .orElseThrow(()-> new ColumnNotFoundException("존재하지 않는 컬럼입니다."));
        Card card = new Card(cardRequestDto, user, columns);

        new CardCollaborators(user, card);

        checkCollaborators(card, user);
        cardRepository.save(card);

        // 슬랙 메시지 전송
        String message = "새로운 카드가 생성되었습니다: " + card.getTitle();
        slackService.sendSlackNotification(message);

        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public CardResponseDto updateCard(CardRequestDto cardRequestDto, Long boardId, User user, Long cardId) {
        Card card = findCard(cardId);
        checkCollaborators(card, user);
        card.update(cardRequestDto);

        // 슬랙 메시지 전송
        String message = "카드가 수정되었습니다: " + card.getTitle();
        slackService.sendSlackNotification(message);

        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public void deleteCard(User user, Long boardId, Long cardId) {
        Card card = findCard(cardId);
        checkCollaborators(card, user);
        cardRepository.delete(card);

        // 슬랙 메시지 전송
        String message = "카드가 삭제되었습니다: " + card.getTitle();
        slackService.sendSlackNotification(message);
    }


    // 카드에 유저 할당
    @Transactional
    public ResponseEntity<ApiResponseDto> collaborator(CardCollaboratorsRequestDto cardCollaboratorsRequestDto, Long boardId, User user) {
        // 할당 할 유저 조회
        User collaborator = findUser(user, cardCollaboratorsRequestDto.getUsername(), boardId);

        // 할당 할 카드 조회
        Card tagetCard = findCard(cardCollaboratorsRequestDto.getCardId());

        // 본인이 생성한 카드가 아닌 경우, 다른 유저를 할당 할 수 없음
        if (!tagetCard.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("카드를 생성한 유저가 아닙니다.");
        }

        // CardCollaborators 와 관계 매핑
        CardCollaborators cardCollaborators = new CardCollaborators(collaborator, tagetCard);

        // db 저장
        cardCollaboratorRepository.save(cardCollaborators);

        return ResponseEntity.ok().body(new ApiResponseDto("작업자 할당 완료!", HttpStatus.OK.value()));
    }


    // 초대된 유저 조회
    public List<CardCollaboratorsResponseDto> getCollaborator(Long id) {
        // 카드 조회
        Card card = findCard(id);

        return card.getCardCollaborators().stream()
                .map(cardCollaborators -> new CardCollaboratorsResponseDto(cardCollaborators.getUser().getUsername()))
                .toList();
    }
    @Override
    public CardResponseDto updateDueDate(Long cardId, Long boardId, String dueDate, User user) throws DateTimeParseException {
        // 카드 조회
        Card card = findCard(cardId);

        // formatter 에 dueDate 형식을 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 입력받은 dueDate 를 formatter 형식으로 파싱
        // 만약 dueDate 의 형식이 올바르지 않다면 Exception 발생
        LocalDateTime localDateTime = LocalDateTime.parse(dueDate, formatter);

        // 업데이트 메소드
        card.updateDueDate(localDateTime);

        return new CardResponseDto(card);

    }
  
    // ===================== 공통 메서드 ===================== //

    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() ->
                new CardNotFoundException("선택한 카드는 존재하지 않습니다.")
        );
    }

    // 유저 조회
    public User findUser(User user, String username, Long boardId) {
        User user1 = userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(()->
                new UserNotFoundException("존재하지 않는 유저입니다"));

        if (user.getUsername().equals(username)) {
            throw new SelfAssignmentNotAllowedException("본인은 할당 할 수 없습니다");
        }

        return userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    // 할당 된 유저 검증
    private void checkCollaborators(Card card, User user) {

        // 조회한 카드에서 collaborator 리스트 가져오기
        List<CardCollaborators> cardCollaborators = card.getCardCollaborators();

        // 유저 네임 리스트 생성
        List<String> usernames = new ArrayList<>();

        // CardCollaborator에서 유저이름을 추출하고, 유저네임 리스트에 저장
        for (CardCollaborators cardCollaborator : cardCollaborators) {
            usernames.add(cardCollaborator.getUser().getUsername());
        }

        // 현재 인가된 사용자의 이름과 비교
        if (!usernames.contains(user.getUsername())) {
            throw new AccessDeniedException("할당된 유저가 아닙니다.");
        }
    }
}
