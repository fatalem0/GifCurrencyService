package com.example.GifCurrencyService.service;

import com.example.GifCurrencyService.model.ExchangeResponse;

import java.util.Map;

public interface OpenExchangeService {
    ExchangeResponse getTodayCurrency();

    ExchangeResponse getPreviousCurrency();

    boolean getCompareMoney(Map<String, Double> changeCurrency, Map<String, Double> changeCurrencyHistory);
}
