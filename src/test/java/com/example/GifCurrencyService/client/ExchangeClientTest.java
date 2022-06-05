package com.example.GifCurrencyService.client;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;

import com.example.GifCurrencyService.config.ExchangeConfiguration;
import com.example.GifCurrencyService.model.ExchangeResponse;
import com.example.GifCurrencyService.service.OpenExchangeServiceImpl;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeClientTest {
    @Autowired
    private ExchangeClient exchangeClient;

    @Autowired
    private OpenExchangeServiceImpl openExchangeService;

    @Autowired
    private ExchangeConfiguration exchangeConfiguration;

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
                        .withBodyFile("test.json")
                ));
    }

    @Test
    public void shouldReturnUserResponseLatest() {
        ExchangeResponse exchangeResponse = exchangeClient.getLatestCurrency(exchangeConfiguration.getAppId(),
                exchangeConfiguration.getBase());
        Assertions.assertThat(exchangeResponse.getRates().get("USD")).isEqualTo(1);
        Assertions.assertThat(exchangeResponse.getRates().size()).isEqualTo(171);
        Assertions.assertThat(exchangeResponse.getRates().get("RUB")).isEqualTo(63.360006);
        Assertions.assertThat(exchangeResponse.getTimestamp()).isEqualTo(1654455619);
        Assertions.assertThat(exchangeResponse.getLicense()).isEqualTo("https://openexchangerates.org/license");
        Assertions.assertThat(exchangeResponse.getDisclaimer()).isEqualTo("Usage subject to terms: https://openexchangerates.org/terms");
    }

    @Test
    public void testGetCurrencyByDate() {
        ExchangeResponse exchangeResponse = exchangeClient
                .getCurrencyByDate(exchangeConfiguration.getAppId(),
                        exchangeConfiguration.getBase(),
                        "2022-06-05");
        Assertions.assertThat(exchangeResponse.getRates().get("USD")).isEqualTo(1);
        Assertions.assertThat(exchangeResponse.getRates().size()).isEqualTo(171);
        Assertions.assertThat(exchangeResponse.getRates().get("RUB")).isEqualTo(63.360006);
        Assertions.assertThat(exchangeResponse.getTimestamp()).isEqualTo(1654455619);
    }

    @Test
    public void testConfiguration(){
        Assertions.assertThat(exchangeConfiguration.getAppId()).isEqualTo("1111");
        Assertions.assertThat(exchangeConfiguration.getBase()).isEqualTo("USD");
        Assertions.assertThat(exchangeConfiguration.getOpponent()).isEqualTo("EUR");
        Assertions.assertThat(exchangeConfiguration.getDaysAgo()).isEqualTo(1);
    }

    @Test
    public void testGetTodayCurrency(){
        LocalDateTime thisDay = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ExchangeResponse exchangeResponse = openExchangeService.getTodayCurrency();
        Assertions.assertThat(exchangeResponse.getRates().get("USD")).isEqualTo(1);
        Assertions.assertThat(exchangeResponse.getRates().get("RUB")).isEqualTo(63.360006);
        Assertions.assertThat(exchangeResponse.getDateCurrency().format(formatter)).isEqualTo(thisDay.format(formatter));
        Assertions.assertThat(exchangeResponse.getOpponent()).isEqualTo("EUR");
    }

    @Test
    public void testGetPreviousCurrency(){
        LocalDateTime historyDay = LocalDateTime.now().minusDays(exchangeConfiguration.getDaysAgo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ExchangeResponse exchangeResponse = openExchangeService.getPreviousCurrency();
        Assertions.assertThat(exchangeResponse.getRates().get("USD")).isEqualTo(1);
        Assertions.assertThat(exchangeResponse.getRates().get("RUB")).isEqualTo(63.360006);
        Assertions.assertThat(exchangeResponse.getDateCurrency().format(formatter)).isEqualTo(historyDay.format(formatter));
    }
}
