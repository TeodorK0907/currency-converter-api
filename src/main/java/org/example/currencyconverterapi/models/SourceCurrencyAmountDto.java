package org.example.currencyconverterapi.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class SourceCurrencyAmountDto {
    private static final String INVALID_AMOUNT_ERROR_MESSAGE = "Amount cannot be less than 1.";
    @NotNull
    @Positive
    @Min(value = 1, message = INVALID_AMOUNT_ERROR_MESSAGE)
    private BigDecimal amount;

    public SourceCurrencyAmountDto () {

    }

    public BigDecimal getAmount() {
        return amount;
    }
}
