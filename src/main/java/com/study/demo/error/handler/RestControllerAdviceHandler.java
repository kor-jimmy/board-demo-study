package com.study.demo.error.handler;

import com.study.demo.error.ErrorResponse;
import com.study.demo.error.exception.DuplicateAccountException;
import com.study.demo.error.exception.DuplicateEmailException;
import com.study.demo.error.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

@RestControllerAdvice
public class RestControllerAdviceHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(400);
        response.setException("ValidationException");
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<?> handleAccountDuplicateException(DuplicateAccountException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(409);
        response.setException("DuplicateAccountException");
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleEmailDuplicateException(DuplicateEmailException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(409);
        response.setException("DuplicateEmailException");
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(401);
        response.setException("UnauthorizedException");
        response.setErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
