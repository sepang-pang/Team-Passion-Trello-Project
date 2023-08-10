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
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.repository.UserRepository;
import com.passion.teampassiontrelloproject.userBoard.repository.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserBoardRepository userBoardRepository;
    private final CardCollaboratorRepository cardCollaboratorRepository;
    private final ColumnsRepository columnsRepository;

    @Transactional
    @Override
    public CardResponseDto getCardById(Long id) {
        Card card = findCard(id);
        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public CardResponseDto createdCard(CardRequestDto cardRequestDto, Long boardId, Long columnId, User user) {
        authority(user, boardId);
        Columns columns = columnsRepository.findById(columnId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
        Card card = new Card(cardRequestDto, user, columns);

        new CardCollaborators(user, card);

        checkCollaborators(card, user);
        cardRepository.save(card);
        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public CardResponseDto updateCard(CardRequestDto cardRequestDto, User user, Long boardId, Long cardId) {
        authority(user, boardId);
        Card card = findCard(cardId);
        checkCollaborators(card, user);
        card.update(cardRequestDto);
        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public void deleteCard(User user, Long boardId, Long cardId) {
        authority(user, boardId);
        Card card = findCard(cardId);
        checkCollaborators(card, user);
        cardRepository.delete(card);
    }


    // 카드에 유저 할당
    @Transactional
    public ResponseEntity<ApiResponseDto> collaborator(CardCollaboratorsRequestDto cardCollaboratorsRequestDto, User user, Long boardId) {
        authority(user, boardId);
        // 할당 할 유저 조회
        User collaborator = findUser(user, cardCollaboratorsRequestDto.getUsername(), boardId);

        // 할당 할 카드 조회
        Card tagetCard = findCard(cardCollaboratorsRequestDto.getCardId());

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
  
    // ===================== 공통 메서드 ===================== //

    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() ->
                new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }

    // 보드에 속한 유저가 아니라면 카드에 대한 권한을 가질 수 없음
    public void authority(User user, Long boardId) {
        userBoardRepository.findByUserIdAndBoardId(user.getId(), boardId).orElseThrow(() ->
                new IllegalArgumentException("보드에 속한 유저가 아닙니다."));
    }

    // 유저 조회
    public User findUser(User user, String username, Long boardId) {
        User user1 = userRepository.findByUsername(username).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 유저입니다"));

        authority(user1, boardId);
        if (user.getUsername().equals(username)) {
            throw new IllegalArgumentException("본인은 할당 할 수 없습니다");
        }

        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("할당되지 않은 카드입니다."));
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
            throw new IllegalArgumentException("할당된 유저가 아닙니다.");
        }
    }
}
