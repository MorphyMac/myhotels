package com.epam.myhotels.reservationservice.controller.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ApplicationErrorDto {

    private String errorRefId;
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorDto> fieldErrors;

    public ApplicationErrorDto(Integer status, String error, String message, String path,
                               List<FieldErrorDto> fieldErrors) {
        this.errorRefId = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldErrorDto {
        private String field;
        private String reason;
    }
}