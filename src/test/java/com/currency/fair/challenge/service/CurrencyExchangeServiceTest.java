package com.currency.fair.challenge.service;

import com.currency.fair.challenge.TestUtil;
import com.currency.fair.challenge.except.InvalidParamException;
import com.currency.fair.challenge.model.TradeMessage;
import com.currency.fair.challenge.request.TradeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeServiceTest {

    CurrencyExchangeService service;

    @BeforeEach
    void setup() {
        service = new CurrencyExchangeService();
    }

    @Test
    void updateTradeMessage_Success_GivenValidRequest() {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();

        // when
        service.updateTradeMessage(request);

        // then
        int expectedId = 1;
        TradeMessage expectedMessage = TestUtil.getTradeMessageFromRequest(request);

        assertFalse(service.tradeMessageCache.isEmpty());
        assertEquals(1, service.tradeMessageCache.values().size());
        assertEquals(expectedMessage, service.tradeMessageCache.get(expectedId));
    }

    @Test
    void updateTradeMessage_Success_IfCalledMethodMultipleTimes() {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();
        service.updateTradeMessage(request);

        request.setUserId(16342);
        service.updateTradeMessage(request);

        request.setUserId(32153);
        request.setCurrencyTo("HKD");
        request.setAmountBuy(9497.8);
        request.setRate(9.4978);

        // when
        service.updateTradeMessage(request);

        // then
        int expectedId = 3;
        TradeMessage expectedMessage = TestUtil.getTradeMessageFromRequest(request);

        assertFalse(service.tradeMessageCache.isEmpty());
        assertEquals(1, service.tradeMessageCache.values().size());
        assertEquals(expectedMessage, service.tradeMessageCache.get(expectedId));
    }

    @Test
    void updateTradeMessage_ThrowException_IfInvalidAmountBuy() {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();
        request.setAmountBuy(request.getAmountSell());

        // then
        assertThrows(InvalidParamException.class, () -> service.updateTradeMessage(request));
    }

    @Test
    void getLatestTradeMessage_Success_IfCalledUpdateTradeMessageMultipleTimes() {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();

        service.updateTradeMessage(request);

        request.setUserId(16342);
        service.updateTradeMessage(request);

        request.setUserId(32153);
        request.setCurrencyTo("HKD");
        request.setAmountBuy(9497.8);
        request.setRate(9.4978);
        service.updateTradeMessage(request);

        // when
        TradeMessage actual = service.getLatestTradeMessage();

        // then
        TradeMessage expected = TestUtil.getTradeMessageFromRequest(request);

        assertEquals(expected, actual);
    }

}