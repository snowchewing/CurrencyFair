package com.currency.fair.challenge.controller;

import com.currency.fair.challenge.TestUtil;
import com.currency.fair.challenge.request.TradeRequest;
import com.currency.fair.challenge.service.CurrencyExchangeService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyExchangeControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Gson gson;

    @Autowired
    CurrencyExchangeService service;

    @Test
    void trade_Success_GivenValidTradeRequest() throws Exception {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();
        String json = toJson(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/trade").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        assertEquals(TestUtil.getTradeMessageFromRequest(request), service.getLatestTradeMessage());
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
    void trade_ReturnError_GivenInvalidTradeRequest(TradeRequest request) throws Exception {
        // given
        String json = toJson(request);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/trade").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void tradeMessage_Success_AfterCallingTradeOnce() throws Exception {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/trade")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tradeMessage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // then
        String actual = result.getResponse().getContentAsString();
        String expected = toJson(TestUtil.getTradeMessageFromRequest(request));
        assertEquals(expected, actual);
    }

    @Test
    void tradeMessage_Success_AfterCallingTradeMultipleTimes() throws Exception {
        // given
        TradeRequest request = TestUtil.getSampleTradeRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/trade")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

        request.setUserId(4251);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/trade")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

        request.setRate(9.467);
        request.setAmountBuy(9467);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/trade")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tradeMessage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // then
        String actual = result.getResponse().getContentAsString();
        String expected = toJson(TestUtil.getTradeMessageFromRequest(request));
        assertEquals(expected, actual);
    }

    String toJson(Object value) {
        return gson.toJson(value);
    }
}