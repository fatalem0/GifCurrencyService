package com.example.GifCurrencyService.controller;

import com.example.GifCurrencyService.model.RandomGif;
import com.example.GifCurrencyService.service.GiphyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ExchangeRateController {
    @Autowired
    private final GiphyService giphyService;

    @GetMapping("/currency")
    public String getHelpByCurrency(@RequestParam(name = "code") String code) {
        RandomGif giphyByRateWithCode = giphyService.getGiphy(code);
        return giphyByRateWithCode.getUrl();
    }
}
