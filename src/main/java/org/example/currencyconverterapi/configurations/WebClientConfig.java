package org.example.currencyconverterapi.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${currency.beacon.api-url}")
    private String currencyBeaconURL;

    @Bean
    public WebClient webClient() {
        return WebClient.create(currencyBeaconURL);
    }
}
