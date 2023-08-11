package com.passion.teampassiontrelloproject.card.controller;

import com.google.protobuf.Api;
import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.card.dto.CardResponseDto;
import com.passion.teampassiontrelloproject.card.service.CardServiceImpl;
import com.passion.teampassiontrelloproject.cardCollaborators.dto.CardCollaboratorsRequestDto;
import com.passion.teampassiontrelloproject.cardCollaborators.dto.CardCollaboratorsResponseDto;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {
    private final CardServiceImpl cardService;

    // 카드 선택조회
    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> getCardById(@PathVariable Long cardId) {
        CardResponseDto card = cardService.getCardById(cardId);
        if (card != null) {
            return new ResponseEntity<>(card, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 카드 작성
    @PostMapping
    public ResponseEntity<CardResponseDto> createdCard(@RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto createdCard = cardService.createdCard(cardRequestDto, cardRequestDto.getBoardId(), cardRequestDto.getColumnId(), userDetails.getUser());
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    // 카드 수정
    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId, @RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto updateCard = cardService.updateCard(cardRequestDto, userDetails.getUser(), cardRequestDto.getBoardId(), cardId);
        if (updateCard != null) {
            return new ResponseEntity<>(updateCard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long cardId, @RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCard(userDetails.getUser(), cardRequestDto.getBoardId(), cardId);
        return ResponseEntity.ok().body(new ApiResponseDto("카드 삭제 성공", 200));
    }

    // 작업자 할당
    @PostMapping("/collaborator")
    public ResponseEntity<ApiResponseDto> collaborator(@RequestBody CardCollaboratorsRequestDto cardCollaboratorsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.collaborator(cardCollaboratorsRequestDto, userDetails.getUser(), cardCollaboratorsRequestDto.getBoardId());
    }

    // 할당 된 유저 조회
    @GetMapping("/collaborator/{id}")
    public List<CardCollaboratorsResponseDto> getCollaborator(@PathVariable Long id) {
        return cardService.getCollaborator(id);
    }

    // 작업자 변경
}


