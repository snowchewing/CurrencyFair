package com.currency.fair.challenge.config;

import com.currency.fair.challenge.except.InvalidParamException;
import feign.FeignException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class FeignExceptionHandler {

    @ExceptionHandler(InvalidParamException.class)
    public String handleFeignStatusException(InvalidParamException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.getMessage();
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.getMessage();
    }
}
