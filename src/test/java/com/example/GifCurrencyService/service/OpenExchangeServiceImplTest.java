package com.example.GifCurrencyService.service;

import com.example.GifCurrencyService.client.ExchangeClient;
import com.example.GifCurrencyService.model.ExchangeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenExchangeServiceImplTest {
    @Autowired
    OpenExchangeServiceImpl openExchangeService;

    @Mock
    ExchangeClient exchangeClient;

    public static Map<String, Double> currency;
    public static ExchangeResponse exchangeResponse;
    public static Map<String, Double> currencyOpponent;

    @BeforeEach
    public void setup() {

        currency = new HashMap<>();
        currency.put("AED", 3.6731);
        currency.put("AFN", 89.162943);
        currency.put("EUR", 0.932923);
        currency.put("RUB", 63.360006);
        currency.put("USD", 1D);

        currencyOpponent = new HashMap<>();
        currencyOpponent.put("AED", 3.6731);
        currencyOpponent.put("AFN", 89.162943);
        currencyOpponent.put("EUR", 0.932923);
        currencyOpponent.put("RUB", 63.360006);
        currencyOpponent.put("USD", 1D);

        exchangeResponse = new ExchangeResponse(LocalDateTime.now(),"EUR","disclaimer","license",111, "USD", currency);
    }

    @Test
    void testGetLatestCurrency() {
        when(exchangeClient.getLatestCurrency(anyString(), anyString())).thenReturn(
                OpenExchangeServiceImplTest.exchangeResponse);
        ExchangeResponse currencyResponse = exchangeClient.getLatestCurrency("1111", "USD");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Assertions.assertThatObject(currencyResponse).isEqualTo(OpenExchangeServiceImplTest.exchangeResponse);
        Assertions.assertThat(currencyResponse.getBase()).isEqualTo("USD");
        Assertions.assertThat(currencyResponse.getRates().size()).isEqualTo(5);
        Assertions.assertThat(currencyResponse.getRates().get("EUR")).isEqualTo(0.932923);
        Assertions.assertThat(currencyResponse.getDateCurrency().format(formatter)).isEqualTo(LocalDateTime.now().format(formatter));
        verify(exchangeClient).getLatestCurrency("1111", "USD");
    }

    @Test
    void testGetCurrencyByDate() {
        when(exchangeClient.getCurrencyByDate(anyString(), anyString(), anyString())).thenReturn(
                OpenExchangeServiceImplTest.exchangeResponse);
        ExchangeResponse currencyResponse = exchangeClient.getCurrencyByDate("1111", "USD", "2022-06-05");
        Assertions.assertThat(currencyResponse.getRates().get("RUB")).isEqualTo(63.360006);
        verify(exchangeClient).getCurrencyByDate("1111", "USD", "2022-06-05");
    }

//    @Test
//    void getCompareMoney() {
//        boolean actualBol = openExchangeService.getCompareMoney(currency, currencyOpponent);
//        Assertions.assertThat(actualBol).isEqualTo(true);
//    }
}
