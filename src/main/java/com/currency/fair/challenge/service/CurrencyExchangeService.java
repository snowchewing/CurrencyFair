package com.currency.fair.challenge.service;

import com.currency.fair.challenge.except.InvalidParamException;
import com.currency.fair.challenge.model.TradeMessage;
import com.currency.fair.challenge.request.TradeRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CurrencyExchangeService {

    ConcurrentHashMap<Integer, TradeMessage> tradeMessageCache = new ConcurrentHashMap<>();

    AtomicInteger latestId = new AtomicInteger(0);

    public void updateTradeMessage(TradeRequest request) {
        validateTradeRequest(request);
        latestId.addAndGet(1);
        tradeMessageCache.put(latestId.intValue(), requestToTradeMessage(request));
        removeOldTradeMessages(latestId.intValue() - 1);
    }

    public TradeMessage getLatestTradeMessage() {
        return tradeMessageCache.get(latestId.intValue());
    }

    private TradeMessage requestToTradeMessage(TradeRequest request) {
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

    private void validateTradeRequest(TradeRequest request) {
        if (request.getAmountSell() * request.getRate() != request.getAmountBuy()) {
            throw new InvalidParamException("Amount buy should be equal to amount sell * rate");
        }
    }

    private void removeOldTradeMessages(int oldId) {
        tradeMessageCache.remove(oldId);
    }
}
