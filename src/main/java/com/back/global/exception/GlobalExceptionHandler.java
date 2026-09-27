package com.back.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {

        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "서버 오류가 발생했습니다.";

        // 1. DTO 입력값 검증 실패
        if (e instanceof MethodArgumentNotValidException ex) {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getBindingResult()
                    .getFieldErrors()
                    .get(0)
                    .getDefaultMessage();
        }

        // 2. 직접 지정한 HTTP 오류
        else if (e instanceof ResponseStatusException ex) {
            status = ex.getStatusCode();
            message = ex.getReason();
        }

        // 3. Spring에서 발생한 HTTP 오류
        else if (e instanceof ErrorResponse ex) {
            status = ex.getStatusCode();
            message = "잘못된 요청입니다.";
        }

        return ResponseEntity
                .status(status)
                .body(Map.of(
                        "status", status.value(),
                        "message", message
                ));
    }
}