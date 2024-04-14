package org.example.currencyconverterapi.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

public class Conversion {

    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;
    private int id;
    private BigDecimal amount;
    private LocalDateTime timeStamp;
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private final String UUID;

    public Conversion () {
        this.UUID = java.util.UUID.randomUUID().toString();
    }

    public Conversion(double amount, Currency targetCurrency) {
        this(amount, targetCurrency, DEFAULT_ROUNDING);
    }

    private Conversion(double amount, Currency targetCurrency, RoundingMode roundingMode) {
        this.targetCurrency = targetCurrency;
        this.timeStamp = LocalDateTime.now();
        this.UUID = java.util.UUID.randomUUID().toString();
        this.amount = BigDecimal.valueOf(amount)
                .setScale(targetCurrency.getDefaultFractionDigits(), roundingMode);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), DEFAULT_ROUNDING);
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getUUID() {
        return UUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conversion that)) return false;
        return id == that.id && Objects.equals(amount, that.amount)
                && Objects.equals(timeStamp, that.timeStamp)
                && Objects.equals(sourceCurrency, that.sourceCurrency)
                && Objects.equals(targetCurrency, that.targetCurrency)
                && Objects.equals(UUID, that.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, timeStamp, sourceCurrency, targetCurrency, UUID);
    }
}
