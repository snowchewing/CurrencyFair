package com.currency.fair.challenge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TradeMessage {
    private int userId;
    private String currencyFrom;
    private String currencyTo;
    private double amountSell;
    private double amountBuy;
    private double rate;
    private String timePlaced;
    private String originatingCountry;
}
