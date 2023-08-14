package com.passion.teampassiontrelloproject.common.advice.exception;

import com.passion.teampassiontrelloproject.common.advice.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<RestApiException> dateTimeParseExceptionHandler(DateTimeParseException ex) {
        RestApiException restApiException = new RestApiException("형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }


    // 게시글 Not Found
    @ExceptionHandler({BoardNotFoundException.class})
    public ResponseEntity<RestApiException> boardNotFoundExceptionHandler(BoardNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 카드 Not Found
    @ExceptionHandler({CardNotFoundException.class})
    public ResponseEntity<RestApiException> notFoundCommentExceptionHandler(CardNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 컬럼 Not Found
    @ExceptionHandler({ColumnNotFoundException.class})
    public ResponseEntity<RestApiException> columnNotFoundExceptionHandler(ColumnNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }


    // 유저 Not Found
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<RestApiException> userNotFoundExceptionHandler(UserNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 권한 에러
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<RestApiException> accessDeniedExceptionHandler(AccessDeniedException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.FORBIDDEN
        );
    }

    // 중복 에러
    @ExceptionHandler({DuplicateException.class})
    public ResponseEntity<RestApiException> duplicateUserExceptionHandler(DuplicateException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 틀린 암호
    @ExceptionHandler({PasswordMismatchException.class})
    public ResponseEntity<RestApiException> passwordMismatchExceptionHandler(PasswordMismatchException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    // 최근에 입력한 암호
    @ExceptionHandler({RecentPasswordException.class})
    public ResponseEntity<RestApiException> recentPasswordExceptionHandler(RecentPasswordException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<RestApiException> IOExceptionHandler(IOException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.UNAUTHORIZED
        );
    }


    // 본인 초대 금지
    @ExceptionHandler({SelfAssignmentNotAllowedException.class})
    public ResponseEntity<RestApiException> selfAssignmentNotAllowedExceptionHandler(SelfAssignmentNotAllowedException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

     // 초대 받지 못한 보드
    @ExceptionHandler({BoardInvitationNotFoundException.class})
    public ResponseEntity<RestApiException> boardInvitationNotFoundExceptionHandler(BoardInvitationNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 올바르지 않은 Admin Token 값
    @ExceptionHandler({InvalidAdminTokenException.class})
    public ResponseEntity<RestApiException> invalidAdminTokenExceptionHandler(InvalidAdminTokenException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }
}
