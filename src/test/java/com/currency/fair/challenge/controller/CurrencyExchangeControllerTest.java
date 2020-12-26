package com.currency.fair.challenge.controller;

import com.currency.fair.challenge.TestUtil;
import com.currency.fair.challenge.except.InvalidParamException;
import com.currency.fair.challenge.model.TradeMessage;
import com.currency.fair.challenge.request.TradeRequest;
import com.currency.fair.challenge.service.CurrencyExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeControllerTest {

    @Mock
    CurrencyExchangeService service;

    @InjectMocks
    CurrencyExchangeController controller;

    @Test
    void trade_Success_GivenValidTradeRequest() {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();

        // when
        controller.trade(request);

        // then
        Mockito.verify(service).updateTradeMessage(request);
    }

    static Stream<Arguments> provideInvalidTradeRequests() {
        TradeRequest request1 = TestUtil.getSampleTradeRequest();
        TradeRequest request2 = TestUtil.getSampleTradeRequest();
        TradeRequest request3 = TestUtil.getSampleTradeRequest();
        TradeRequest request4 = TestUtil.getSampleTradeRequest();
        TradeRequest request5 = TestUtil.getSampleTradeRequest();
        TradeRequest request6 = TestUtil.getSampleTradeRequest();
        TradeRequest request7 = TestUtil.getSampleTradeRequest();
        TradeRequest request8 = TestUtil.getSampleTradeRequest();
        TradeRequest request9 = TestUtil.getSampleTradeRequest();
        TradeRequest request10 = TestUtil.getSampleTradeRequest();
        TradeRequest request11 = TestUtil.getSampleTradeRequest();
        request1.setCurrencyFrom("");
        request2.setCurrencyTo("");
        request3.setAmountBuy(0);
        request4.setAmountBuy(-1.0);
        request5.setAmountSell(0);
        request6.setAmountSell(-1.0);
        request7.setRate(0);
        request8.setRate(-1.0);
        request9.setTimePlaced("");
        request10.setTimePlaced("2020 Dec 15 23:08:05");
        request11.setOriginatingCountry("");
        return Stream.of(
                Arguments.of(request1), Arguments.of(request2), Arguments.of(request3),
                Arguments.of(request4), Arguments.of(request5), Arguments.of(request6),
                Arguments.of(request7), Arguments.of(request8), Arguments.of(request9),
                Arguments.of(request10), Arguments.of(request11)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTradeRequests")
    void trade_ThrowException_GivenInvalidTradeRequest(TradeRequest request) {
        assertThrows(InvalidParamException.class, () -> controller.trade(request));
    }

    @Test
    void getTradeMessage_Success() {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();
        TradeMessage expected = TestUtil.getTradeMessageFromRequest(request);
        Mockito.when(service.getLatestTradeMessage()).thenReturn(expected);

        // when
        TradeMessage actual = controller.getTradeMessage();

        // then
        assertEquals(expected, actual);
    }

}