package com.currency.fair.challenge.controller;

import com.currency.fair.challenge.except.InvalidParamException;
import com.currency.fair.challenge.model.TradeMessage;
import com.currency.fair.challenge.request.TradeRequest;
import com.currency.fair.challenge.service.CurrencyExchangeService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
@Slf4j
public class CurrencyExchangeController {

    @Autowired
    CurrencyExchangeService service;

    DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive().appendPattern("dd-LLL-yy HH:mm:ss").toFormatter(Locale.ENGLISH);

    @PostMapping("trade")
    public void trade(@RequestBody TradeRequest request) {
        try {
            validateTradeRequest(request);
            service.updateTradeMessage(request);
            log.info("Updated trade message");
        } catch (Exception e) {
            log.error(e.toString());
            throw e;
        }
    }

    @GetMapping("tradeMessage")
    public TradeMessage getTradeMessage() {
        try {
            return service.getLatestTradeMessage();
        } catch (Exception e) {
            log.error(e.toString());
            return TradeMessage.builder().build();
        }
    }

    private void validateTradeRequest(TradeRequest request) {
        if (request.getCurrencyFrom() == "") {
            throw new InvalidParamException("Currency from cannot be empty");
        } else if (request.getCurrencyTo() == "") {
            throw new InvalidParamException("Currency to cannot be empty");
        } else if (request.getAmountBuy() <= 0) {
            throw new InvalidParamException("Amount buy must be larger than 0");
        } else if (request.getAmountSell() <= 0) {
            throw new InvalidParamException("Amount sell must be larger than 0");
        } else if (request.getRate() <= 0) {
            throw new InvalidParamException("Rate must be larger than 0");
        } else if (request.getOriginatingCountry() == "") {
            throw new InvalidParamException("Originating country cannot be empty");
        } else {
            try {
                dateTimeFormatter.parse(request.getTimePlaced());
            } catch (DateTimeParseException e) {
                throw new InvalidParamException("Time placed is not in date time format");
            }
        }
    }
}
