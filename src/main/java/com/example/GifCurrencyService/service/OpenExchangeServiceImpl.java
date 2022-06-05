package com.example.GifCurrencyService.service;

import com.example.GifCurrencyService.client.ExchangeClient;
import com.example.GifCurrencyService.model.ExchangeResponse;
import com.example.GifCurrencyService.config.ExchangeConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenExchangeServiceImpl implements OpenExchangeService {
    public static final LocalDateTime CURRENT_DATE = LocalDateTime.now();

    @Autowired

    private final ExchangeClient client;
    @Autowired
    private final ExchangeConfiguration exchangeConfiguration;

    @Override
    public ExchangeResponse getTodayCurrency() {
        ExchangeResponse currencyResponse = client.getLatestCurrency(exchangeConfiguration.getAppId(),
                exchangeConfiguration.getBase());
        currencyResponse.setOpponent(exchangeConfiguration.getOpponent());
        currencyResponse.setDateCurrency(CURRENT_DATE);
        return currencyResponse;
    }

    @Override
    public ExchangeResponse getPreviousCurrency() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = formatter.format(CURRENT_DATE.minusDays(exchangeConfiguration.getDaysAgo()));
        ExchangeResponse currencyResponse = client
                .getCurrencyByDate(exchangeConfiguration.getAppId(), exchangeConfiguration.getBase(), date);
        currencyResponse.setOpponent(exchangeConfiguration.getOpponent());
        currencyResponse.setDateCurrency(CURRENT_DATE.minusDays(exchangeConfiguration.getDaysAgo()));
        return currencyResponse;
    }

    @Override
    public boolean getCompareMoney(Map<String, Double> changeCurrency,
                                   Map<String, Double> changeCurrencyHistory) {
        double thisDay = changeCurrency.get(exchangeConfiguration.getOpponent());
        double historyDay = changeCurrencyHistory.get(exchangeConfiguration.getOpponent());
        if (historyDay < 1) {
            return thisDay < historyDay;
        }
        return thisDay >= historyDay;
    }
}
