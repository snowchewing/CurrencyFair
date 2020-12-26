package com.currency.fair.challenge.request;

import lombok.Data;

@Data
public class TradeRequest {
    private int userId;
    private String currencyFrom;
    private String currencyTo;
    private double amountSell;
    private double amountBuy;
    private double rate;
    private String timePlaced;
    private String originatingCountry;
}
