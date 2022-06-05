package com.example.GifCurrencyService.client;

import com.example.GifCurrencyService.model.RandomGif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${giphy.url}", name = "GiphyClient")
public interface GiphyClient {
    @GetMapping(value = "/search")
    RandomGif getRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("q") String q,
            @RequestParam("limit") int limit);
}
