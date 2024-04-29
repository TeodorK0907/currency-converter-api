package org.example.currencyconverterapi.mappers;

import org.example.currencyconverterapi.exceptions.InvalidCurrencyCodeException;
import org.example.currencyconverterapi.models.input_dto.CurrencyPairDto;
import org.springframework.stereotype.Component;

import java.util.Currency;
@Component
public class CurrencyMapper {

    public CurrencyPairDto toPairDto(String sourceCurrencyCode, String targetCurrencyCode) {
        CurrencyPairDto pair = new CurrencyPairDto();
        pair.setSource(toCurrencyObj(sourceCurrencyCode));
        pair.setTarget(toCurrencyObj(targetCurrencyCode));
        return pair;
    }

    public Currency toCurrencyObj(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyCodeException(currencyCode);
        }
    }
}
