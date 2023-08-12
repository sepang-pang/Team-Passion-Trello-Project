package com.passion.teampassiontrelloproject.common.advice.exception;

import com.passion.teampassiontrelloproject.common.advice.custom.DuplicateException;
import com.sun.jdi.request.DuplicateRequestException;
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

//    @ExceptionHandler({NullPointerException.class})
//    public ResponseEntity<RestApiException> nullPointerExceptionHandler(NullPointerException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.NOT_FOUND
//        );
//    }
//
//    // 댓글 Not Found
//    @ExceptionHandler({CommentNotFoundException.class})
//    public ResponseEntity<RestApiException> notFoundCommentExceptionHandler(CommentNotFoundException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.NOT_FOUND
//        );
//    }
//
//    // 게시글 Not Found
//    @ExceptionHandler({PostNotFoundException.class})
//    public ResponseEntity<RestApiException> notFoundPostExceptionHandler(PostNotFoundException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.NOT_FOUND
//        );
//    }
//
//    // 좋아요 Not Found
//    @ExceptionHandler({LikeNotFoundException.class})
//    public ResponseEntity<RestApiException> notFoundLikeExceptionHandler(LikeNotFoundException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.NOT_FOUND
//        );
//    }
//
//
//    // 유저 Not Found
//    @ExceptionHandler({UserNotFoundException.class})
//    public ResponseEntity<RestApiException> notFoundUserExceptionHandler(UserNotFoundException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.NOT_FOUND
//        );
//    }
//
////    // 프로필 Not Found
////    @ExceptionHandler({ProfileNotFoundException.class})
////    public ResponseEntity<RestApiException> notFoundProfileExceptionHandler(ProfileNotFoundException ex) {
////        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
////        return new ResponseEntity<>(
////                // HTTP body
////                restApiException,
////                // HTTP status code
////                HttpStatus.NOT_FOUND
////        );
////    }
////
////    // 미디어 Not Found
////    @ExceptionHandler({MediaNotFoundException.class})
////    public ResponseEntity<RestApiException> notFoundMediaExceptionHandler(MediaNotFoundException ex) {
////        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
////        return new ResponseEntity<>(
////                // HTTP body
////                restApiException,
////                // HTTP status code
////                HttpStatus.NOT_FOUND
////        );
////    }
////
////    // 미디어 Upload 관련
////    @ExceptionHandler({MediaUploadException.class})
////    public ResponseEntity<RestApiException> mediaUploadExceptionExceptionHandler(MediaUploadException ex) {
////        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
////        return new ResponseEntity<>(
////                // HTTP body
////                restApiException,
////                // HTTP status code
////                HttpStatus.BAD_REQUEST
////        );
////    }
//
////    // 미디어 Delete 관련
////    @ExceptionHandler({MediaDeleteException.class})
////    public ResponseEntity<RestApiException> mediaDeleteExceptionExceptionHandler(MediaDeleteException ex) {
////        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
////        return new ResponseEntity<>(
////                // HTTP body
////                restApiException,
////                // HTTP status code
////                HttpStatus.BAD_REQUEST
////        );
////    }
//
////    // 미디어 Mismatch
////    @ExceptionHandler({MediaMismatchException.class})
////    public ResponseEntity<RestApiException> mediaMismatchExceptionExceptionHandler(MediaMismatchException ex) {
////        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
////        return new ResponseEntity<>(
////                // HTTP body
////                restApiException,
////                // HTTP status code
////                HttpStatus.BAD_REQUEST
////        );
////    }
//
//    // 중복 요청
//    @ExceptionHandler({DuplicateRequestException.class})
//    public ResponseEntity<RestApiException> duplicateRequestExceptionHandler(DuplicateRequestException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST
//        );
//    }
//
//    // 본인이 아닐 때
//    @ExceptionHandler({AccessDeniedException.class})
//    public ResponseEntity<RestApiException> accessDeniedExceptionHandler(AccessDeniedException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.FORBIDDEN.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.FORBIDDEN
//        );
//    }
//
    //    중복 에러
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
//
//
//    // 틀린 암호
//    @ExceptionHandler({PasswordMismatchException.class})
//    public ResponseEntity<RestApiException> passwordMismatchExceptionHandler(PasswordMismatchException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST
//        );
//    }
//
//    // 최근에 입력한 암호
//    @ExceptionHandler({RecentPasswordException.class})
//    public ResponseEntity<RestApiException> recentPasswordExceptionHandler(RecentPasswordException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST
//        );
//    }
//
//    @ExceptionHandler({IOException.class})
//    public ResponseEntity<RestApiException> IOExceptionHandler(IOException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.UNAUTHORIZED
//        );
//    }
    //
    // 본인 게시글, 댓글 좋아요 금지
//    @ExceptionHandler({SelfLikeNotAllowedException.class})
//    public ResponseEntity<RestApiException> selfLikeNotAllowedExceptionHandler(SelfLikeNotAllowedException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST
//        );
//    }
//
//    // 본인 팔로우 금지
//    @ExceptionHandler({SelfFollowNotAllowedException.class})
//    public ResponseEntity<RestApiException> selfFollowNotAllowedExceptionHandler(SelfFollowNotAllowedException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST
//        );
//    }

    // 존재하지 않을 때 예외
//    @ExceptionHandler({NotExistException.class})
//    public ResponseEntity<RestApiException> notExistExceptionHandler(NotExistException ex) {
//        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(
//                // HTTP body
//                restApiException,
//                // HTTP status code
//                HttpStatus.NOT_FOUND
//        );
//    }

}
