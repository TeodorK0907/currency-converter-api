package org.example.currencyconverterapi;

import org.example.currencyconverterapi.configurations.CurrencyBeaconConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CurrencyBeaconConfigProperties.class)
public class CurrencyConverterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApiApplication.class, args);
    }

}
