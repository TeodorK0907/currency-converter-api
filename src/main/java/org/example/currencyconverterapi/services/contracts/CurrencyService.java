package org.example.currencyconverterapi.services.contracts;

import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.output_dto.ExchangeRateOutputDto;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyService {

    ExchangeRateOutputDto getExchangeRate(Currency source, Currency target);

    void createConversionAmount(Conversion conversion, BigDecimal amount);

    //todo return pagable obj since the list needs to support it
}
