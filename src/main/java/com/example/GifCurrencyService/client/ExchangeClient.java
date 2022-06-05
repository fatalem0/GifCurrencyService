package com.example.GifCurrencyService.client;

import com.example.GifCurrencyService.model.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${exchangeUrl}", name = "CurrencyClient")
public interface ExchangeClient {
    @GetMapping(value = "/latest.json")
    ExchangeResponse getLatestCurrency(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );

    @GetMapping(value = "/historical/{date}.json")
    ExchangeResponse getCurrencyByDate(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base,
            @PathVariable("date") String date
    );

}
