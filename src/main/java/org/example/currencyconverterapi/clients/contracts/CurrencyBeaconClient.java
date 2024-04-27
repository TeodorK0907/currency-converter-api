package org.example.currencyconverterapi.clients.contracts;

import org.example.currencyconverterapi.models.Conversion;

import java.math.BigDecimal;

public interface CurrencyBeaconClient {
    String getExchangeRateResponse(String sourceCurrencyCode);

    String getConversion(Conversion conversion, BigDecimal amount);

}
