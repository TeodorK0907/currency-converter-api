package org.example.currencyconverterapi.mappers;

import org.example.currencyconverterapi.exceptions.InvalidCurrencyCodeException;
import org.springframework.stereotype.Component;

import java.util.Currency;
@Component
public class CurrencyMapper {

    public Currency toCurrencyObj(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyCodeException(currencyCode);
        }
    }
}
