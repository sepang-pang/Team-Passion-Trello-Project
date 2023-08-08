package com.passion.teampassiontrelloproject.card.controller;

import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.card.dto.CardResponseDto;
import com.passion.teampassiontrelloproject.card.service.CardService;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;

    // 카드 선택조회
    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCardById(@PathVariable Long id) {
        CardResponseDto card = cardService.getCardById(id);
        if (card != null) {
            return new ResponseEntity<>(card, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 카드 작성
    @PostMapping
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto createCard = cardService.createPost(cardRequestDto, userDetails.getUser());
        return new ResponseEntity<>(createCard, HttpStatus.CREATED);
    }

    // 카드 수정
    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long id, @RequestBody CardRequestDto cardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto updateCard = cardService.updateCard(id, cardRequestDto, userDetails.getUser());
        if (updateCard != null) {
            return new ResponseEntity<>(updateCard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 카드 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCard(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("카드 삭제 성공", 200));
    }
}


