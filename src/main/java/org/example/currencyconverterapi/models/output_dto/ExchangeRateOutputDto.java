package org.example.currencyconverterapi.models.output_dto;

import java.math.BigDecimal;

public class ExchangeRateOutputDto {

    private final BigDecimal amount;
    private final String currencyCode;

    public ExchangeRateOutputDto(double amount, String currencyCode) {
        this.currencyCode = currencyCode;
        this.amount = BigDecimal.valueOf(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
