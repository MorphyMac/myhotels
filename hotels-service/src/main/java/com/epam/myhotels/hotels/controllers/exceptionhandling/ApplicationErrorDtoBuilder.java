package com.epam.myhotels.hotels.controllers.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
public class ApplicationErrorDtoBuilder {

    private MessageSource messageSource;
    private HttpStatus status;
    private HttpServletRequest request;
    private String errorCode;
    private String message;
    private boolean customMessage;
    private List<Object> messageArgs;
    private Throwable cause;
    private List<ApplicationErrorDto.FieldErrorDto> fieldErrors;

    public ApplicationErrorDtoBuilder(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageArgs = new ArrayList<>();
    }

    public ApplicationErrorDtoBuilder(MessageSource messageSource, HttpServletRequest request) {
        this.messageSource = messageSource;
        this.request = request;
        this.messageArgs = new ArrayList<>();
    }

    public void setCustomMessage(boolean customMessage) {
        this.customMessage = customMessage;
    }

    public ApplicationErrorDtoBuilder addMessageArg(Object messageArg) {
        this.messageArgs.add(messageArg);
        return this;
    }

    public ApplicationErrorDtoBuilder cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public ApplicationErrorDtoBuilder fieldErrors(Exception exception) {
        this.fieldErrors = new ArrayList<>();

        if (exception instanceof ConstraintViolationException) {
            ((ConstraintViolationException) exception).getConstraintViolations().forEach(violation -> fieldErrors
                    .add(new ApplicationErrorDto.FieldErrorDto(violation.getPropertyPath().toString(), violation
                            .getMessage())));
        } else if (exception instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) exception).getFieldErrors().forEach(violation -> fieldErrors
                    .add(new ApplicationErrorDto.FieldErrorDto(violation.getField(), violation.getDefaultMessage())));
        } else {
            // skip field errors
            this.fieldErrors = null;
        }


        return this;
    }

    public ApplicationErrorDtoBuilder errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ApplicationErrorDtoBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ApplicationErrorDtoBuilder isCustomMessage(boolean customMessage) {
        this.customMessage = customMessage;
        return this;
    }

    public ApplicationErrorDtoBuilder request(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public ApplicationErrorDtoBuilder status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ApplicationErrorDto build() {
        Object[] args = null;

        if (!CollectionUtils.isEmpty(messageArgs)) {
            args = messageArgs.toArray();
        }

        String errorMessage;

        // resolve message
        boolean messageEmpty = message != null;
        if (customMessage && messageEmpty) {
            errorMessage = resolveErrorMessage(message, args);
        } else {
            if (messageEmpty) {
                errorMessage = message;
            } else {
                errorMessage = resolveErrorCode(args);
            }
        }

        // resolve if any constraint violation
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            for (ApplicationErrorDto.FieldErrorDto fieldError : fieldErrors) {
                if (fieldError.getReason().startsWith("Field")) {
                    errorMessage = errorMessage.concat(" ").concat(fieldError.getReason());
                } else {
                    errorMessage = errorMessage
                            .concat(String.format(" Field %s %s.", fieldError.getField(), fieldError.getReason()));
                }
            }
        }

        return new ApplicationErrorDto(status.value(), errorCode, errorMessage, request == null ? null : request
                .getServletPath(), fieldErrors);
    }

    public String resolveErrorCode(final Object[] args) {
        try {
            return messageSource.getMessage("errors." + errorCode, args, Locale.getDefault());
        } catch (Exception e) {
            log.warn("Improper use of messageSource mapping in error handling", e);
            return message;
        }
    }

    public String resolveErrorMessage(String message, Object[] args) {
        try {
            return messageSource.getMessage("errors." + message, args, Locale.getDefault());
        } catch (Exception e) {
            log.warn("Improper use of messageSource mapping in error handling", e);
            return message;
        }
    }
}