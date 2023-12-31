package com.passion.teampassiontrelloproject.card.service;

import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.card.dto.CardResponseDto;
import com.passion.teampassiontrelloproject.cardCollaborators.dto.CardCollaboratorsRequestDto;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CardService {
//    CardResponseDto getCardById(Long id);
    CardResponseDto createdCard(CardRequestDto cardRequestDto, Long boardId, Long columnId, User user);
    CardResponseDto updateCard(CardRequestDto cardRequestDto, Long boardId, User user, Long cardId);
    void deleteCard(User user, Long boardId, Long cardId);
    CardResponseDto updateDueDate (Long cardId, Long boardId, String dueDate, User user);
    ResponseEntity<ApiResponseDto> collaborator(CardCollaboratorsRequestDto cardCollaboratorsRequestDto, Long boardId, User user);
}