package com.example.GifCurrencyService.client;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;

import com.example.GifCurrencyService.config.GiphyConfiguration;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GiphyClientTest {
    @Autowired
    private GiphyConfiguration giphyConfiguration;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration
            .options().port(8888)
            .notifier(new ConsoleNotifier(true))
            .extensions(new ResponseTemplateTransformer(true)));

    @Before
    public void setup() {
        wireMockRule.stubFor(WireMock.get(anyUrl())
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("testGiphi.json")
                ));
    }

    @Test
    public void testConfiguration() {
        Assertions.assertThat(giphyConfiguration.getLimit()).isEqualTo(1);
        Assertions.assertThat(giphyConfiguration.getGiphyKey()).isEqualTo("2222");
        Assertions.assertThat(giphyConfiguration.getRichParam()).isEqualTo("rich");
        Assertions.assertThat(giphyConfiguration.getBrokeParam()).isEqualTo("broke");
    }
}
