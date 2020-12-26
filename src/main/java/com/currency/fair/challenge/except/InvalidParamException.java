package com.currency.fair.challenge.except;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParamException extends FeignException {

    public static final long serialVersionUID = 1L;

    private static final int STATUS = HttpStatus.BAD_REQUEST.value();

    public InvalidParamException(String message) {
        super(InvalidParamException.STATUS, message);
    }
}
