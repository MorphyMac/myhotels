package com.epam.myhotels.api.users.controller.exceptionhandling;

import com.epam.myhotels.api.users.service.exception.DuplicateUserException;
import com.epam.myhotels.api.users.service.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ApplicationErrorDto> genericAccessDeniedException(AccessDeniedException ex,
                                                                            HttpServletRequest request) {
        ApplicationErrorDto errorDto = new ApplicationErrorDtoBuilder(messageSource, request)
                .status(HttpStatus.FORBIDDEN).errorCode(ApiErrorCode.FORBIDDEN).message(ex.getMessage()).build();

        log.error("Generic AccessDeniedException caught. error-ref-id = {}", errorDto.getErrorRefId(), ex);
        return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ApplicationErrorDto> genericConstraintViolationException(ConstraintViolationException ex,
                                                                                   HttpServletRequest request) {
        ApplicationErrorDto errorDto = new ApplicationErrorDtoBuilder(messageSource, request)
                .status(HttpStatus.BAD_REQUEST).errorCode(ApiErrorCode.VALIDATION_ERROR).fieldErrors(ex).build();

        log.error("Generic ConstraintViolationException caught. error-ref-id = {}", errorDto.getErrorRefId(), ex);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApplicationErrorDto> genericMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ApplicationErrorDto errorDto = new ApplicationErrorDtoBuilder(messageSource, request)
                .status(HttpStatus.BAD_REQUEST).errorCode(ApiErrorCode.BAD_REQUEST).message(ex.getMessage()).build();

        log.error("Generic MethodArgumentTypeMismatchException caught. error-ref-id = {}", errorDto
                .getErrorRefId(), ex);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApplicationErrorDto> genericMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ApplicationErrorDto errorDto = new ApplicationErrorDtoBuilder(messageSource, request)
                .status(HttpStatus.BAD_REQUEST).errorCode(ApiErrorCode.BAD_REQUEST).fieldErrors(ex).build();

        log.error("Generic MethodArgumentTypeMismatchException caught. error-ref-id = {}", errorDto
                .getErrorRefId(), ex);
        return ResponseEntity.badRequest().body(errorDto);
    }


    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ApplicationErrorDto> genericException(RuntimeException ex, HttpServletRequest request) {
        ApplicationErrorDto errorDto = new ApplicationErrorDtoBuilder(messageSource, request)
                .status(HttpStatus.INTERNAL_SERVER_ERROR).errorCode(ApiErrorCode.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage()).build();

        log.error("Generic RuntimeException caught. error-ref-id = {}", errorDto.getErrorRefId(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(value = {UserNotFoundException.class, DuplicateUserException.class})
    public ResponseEntity<ApplicationErrorDto> customExceptionHandling(Exception ex, HttpServletRequest request) {
        ApplicationErrorDto errorDto = new ApplicationErrorDtoBuilder(messageSource, request)
                .status(HttpStatus.BAD_REQUEST).errorCode(ApiErrorCode.DATA_INTEGRITY_ERROR).message(ex.getMessage())
                .build();

        log.error("Generic UserNotFoundException/DuplicateUserException caught. error-ref-id = {}", errorDto
                .getErrorRefId(), ex);
        return ResponseEntity.badRequest().body(errorDto);
    }
}