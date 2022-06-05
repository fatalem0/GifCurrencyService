package com.example.GifCurrencyService.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GiphyConfiguration {
    @Value("${giphy.url}")
    private String url;

    @Value("${giphy.key}")
    private String giphyKey;

    @Value("${giphy.param.rich}")
    private String richParam;

    @Value("${giphy.param.broke}")
    private String brokeParam;

    @Value("${giphy.limit}")
    private int limit;
}
