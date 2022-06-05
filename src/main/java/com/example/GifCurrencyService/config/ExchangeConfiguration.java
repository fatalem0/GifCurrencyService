package com.example.GifCurrencyService.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ExchangeConfiguration {
    @Value("${param.appId}")
    String appId;
    @Value("${param.base}")
    String base;
    @Value("${param.opponent}")
    String opponent;
    @Value("${param.daysAgo}")
    Integer daysAgo;
}
