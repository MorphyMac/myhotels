package com.epam.myhotels.hotels.controllers.exceptionhandling;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiErrorCode {

    public static final String FORBIDDEN = "Forbidden";
    public static final String VALIDATION_ERROR = "Validation.constraint";
    public static final String BAD_REQUEST = "BadRequest";
    public static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    public static final String DATA_INTEGRITY_ERROR = "DataIntegrityError";
}