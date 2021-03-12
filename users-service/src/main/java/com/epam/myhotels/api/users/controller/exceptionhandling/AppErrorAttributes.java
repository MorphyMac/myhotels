package com.epam.myhotels.api.users.controller.exceptionhandling;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

public class AppErrorAttributes extends DefaultErrorAttributes {

    private MessageSource messageSource;

    public AppErrorAttributes(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    /*
     * error handling for servlet container issue
     * */
    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest,
                                                  final ErrorAttributeOptions errorAttributeOptions) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, errorAttributeOptions);

        ApplicationErrorDto errorDto = new ApplicationErrorDtoBuilder(messageSource)
                .status(HttpStatus.INTERNAL_SERVER_ERROR).errorCode(ApiErrorCode.INTERNAL_SERVER_ERROR)
                .message((String) defaultErrorAttributes.getOrDefault("message", "no message available")).build();

        HashMap<String, Object> response = new HashMap<>();

        response.put("errorRefId", errorDto.getErrorRefId());
        response.put("timestamp", errorDto.getTimestamp());
        response.put("status", errorDto.getStatus().toString());
        response.put("error", errorDto.getError());
        response.put("message", errorDto.getMessage());
        response.put("path", errorDto.getPath());
        response.put("contextPath", webRequest.getContextPath());

        return response;
    }
}