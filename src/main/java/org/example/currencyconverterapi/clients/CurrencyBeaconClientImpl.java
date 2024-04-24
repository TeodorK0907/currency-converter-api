package org.example.currencyconverterapi.clients;

import org.example.currencyconverterapi.clients.contracts.CurrencyBeaconClient;
import org.example.currencyconverterapi.configurations.WebClientConfig;
import org.example.currencyconverterapi.models.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Component
public class CurrencyBeaconClientImpl implements CurrencyBeaconClient {

    private final String API_KEY_REQUEST_PARAM = "api_key";
    private final String EXCHANGE_RATE_ENDPOINT = "/latest";
    private final String BASE_CURRENCY_REQUEST_PARAM = "base";
    private final String CONVERT_ENDPOINT = "/convert";
    private final String FROM_REQUEST_PARAM = "from";
    private final String TO_REQUEST_PARAM = "to";
    private final String AMOUNT_REQUEST_PARAM = "amount";

    @Value("${currency.beacon.api-key}")
    private String key;

    private final WebClientConfig webClient;

    @Autowired
    public CurrencyBeaconClientImpl(WebClientConfig webClient) {
        this.webClient = webClient;
    }

    @Override
    public String getExchangeRateResponse(String sourceCurrencyCode) {
        WebClient client = webClient.webClient();
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(EXCHANGE_RATE_ENDPOINT)
                        .queryParam(API_KEY_REQUEST_PARAM, key)
                        .queryParam(BASE_CURRENCY_REQUEST_PARAM, sourceCurrencyCode)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String getConversion(Conversion conversion, BigDecimal amount) {
        WebClient client = webClient.webClient();
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CONVERT_ENDPOINT)
                        .queryParam(API_KEY_REQUEST_PARAM, key)
                        .queryParam(FROM_REQUEST_PARAM, conversion.getSourceCurrency())
                        .queryParam(TO_REQUEST_PARAM, conversion.getTargetCurrency())
                        .queryParam(AMOUNT_REQUEST_PARAM, amount.toBigInteger())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
