package com.currency.fair.challenge;

import com.currency.fair.challenge.model.TradeMessage;
import com.currency.fair.challenge.request.TradeRequest;

import java.time.Instant;

public class TestUtil {

    public static TradeRequest getSampleTradeRequest() {
        TradeRequest request = new TradeRequest();
        request.setUserId(134256);
        request.setCurrencyFrom("EUR");
        request.setCurrencyTo("GBP");
        request.setAmountSell(1000);
        request.setAmountBuy(747.10);
        request.setRate(0.7471);
        request.setTimePlaced("25-DEC-20 10:27:44");
        request.setOriginatingCountry("FR");
        return request;
    }

    public static TradeMessage getTradeMessageFromRequest(TradeRequest request) {
        return TradeMessage.builder()
                .userId(request.getUserId())
                .currencyFrom(request.getCurrencyFrom())
                .currencyTo(request.getCurrencyTo())
                .amountSell(request.getAmountSell())
                .amountBuy(request.getAmountBuy())
                .rate(request.getRate())
                .timePlaced(request.getTimePlaced())
                .originatingCountry(request.getOriginatingCountry())
                .build();
    }
}
