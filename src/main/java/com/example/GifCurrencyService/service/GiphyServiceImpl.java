package com.example.GifCurrencyService.service;

import com.example.GifCurrencyService.client.GiphyClient;
import com.example.GifCurrencyService.config.GiphyConfiguration;
import com.example.GifCurrencyService.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiphyServiceImpl implements GiphyService {
    @Autowired
    private final OpenExchangeService openExchangeService;

    @Autowired
    private final GiphyClient giphyClient;

    @Autowired
    private final GiphyConfiguration giphyConfiguration;

    @Override
    public RandomGif getGiphy(String code) {
        ExchangeResponse todayCurrency = openExchangeService.getTodayCurrency();
        ExchangeResponse previousCurrency = openExchangeService.getPreviousCurrency();
        if (openExchangeService.getCompareMoney(todayCurrency.getRates(), previousCurrency.getRates())) {
            return giphyClient.getRandomGif(
                    giphyConfiguration.getGiphyKey(),
                    giphyConfiguration.getBrokeParam(),
                    giphyConfiguration.getLimit()
            );
        } else {
            return giphyClient.getRandomGif(
                    giphyConfiguration.getGiphyKey(),
                    giphyConfiguration.getRichParam(),
                    giphyConfiguration.getLimit()
            );
        }
    }
}
