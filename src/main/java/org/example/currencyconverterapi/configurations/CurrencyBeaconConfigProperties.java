package org.example.currencyconverterapi.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("currency.beacon")
public record CurrencyBeaconConfigProperties(String apiUrl, String apiKey) {
}
