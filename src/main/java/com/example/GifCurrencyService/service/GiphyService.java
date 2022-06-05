package com.example.GifCurrencyService.service;

import com.example.GifCurrencyService.model.RandomGif;

public interface GiphyService {
    RandomGif getGiphy(String code);
}
