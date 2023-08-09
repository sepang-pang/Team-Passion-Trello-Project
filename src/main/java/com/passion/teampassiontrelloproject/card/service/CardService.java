package com.passion.teampassiontrelloproject.card.service;

import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.card.dto.CardResponseDto;
import com.passion.teampassiontrelloproject.user.entity.User;

public interface CardService {
    CardResponseDto getCardById(Long id);
    CardResponseDto createdCard(CardRequestDto cardRequestDto, User user);
    CardResponseDto updateCard(Long id, CardRequestDto cardRequestDto, User user);
    void deleteCard(Long id, User user);
}