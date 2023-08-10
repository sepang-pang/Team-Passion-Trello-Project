package com.passion.teampassiontrelloproject.card.service;

import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.card.dto.CardResponseDto;
import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.card.repository.CardRepository;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Transactional
    @Override
    public CardResponseDto getCardById(Long id) {
        Card card = findCard(id);
        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public CardResponseDto createdCard(CardRequestDto cardRequestDto, User user) {
        Card card = new Card(cardRequestDto, user);
        cardRepository.save(card);
        return new CardResponseDto(card);
    }

    @Transactional
    @Override
    public CardResponseDto updateCard(Long id, CardRequestDto cardRequestDto, User user) {
        Card card = findCard(id);

        // 카드 작성자(card.user) 와 요청자(user) 가 같은지 또는 Admin 인지 체크 (아니면 예외발생)
        if (card.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            card.update(cardRequestDto);
        } else {
            throw new IllegalArgumentException("본인 또는 관리자가 아닙니다.");
        }

        return new CardResponseDto(card);
    }


    @Transactional
    @Override
    public void deleteCard(Long id, User user) {
        Card card = findCard(id);

        if (card.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            cardRepository.delete(card);
        } else {
            throw new IllegalArgumentException("본인 또는 관리자가 아닙니다.");
        }
    }

    public Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 카드는 존재하지 않습니다.")
        );
    }



}
