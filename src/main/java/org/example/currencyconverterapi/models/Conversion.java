package org.example.currencyconverterapi.models;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Conversion {

    @Id
    private int id;
    private BigDecimal amount;
    private LocalDateTime timeStamp;
    private String sourceCurrency;
    private String targetCurrency;
    private String UUID;

    public Conversion () {
        this.UUID = java.util.UUID.randomUUID().toString();
    }


    public Conversion(int id, BigDecimal amount,
                      LocalDateTime timeStamp, String sourceCurrency,
                      String targetCurrency, String UUID) {
        this.id = id;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.UUID = UUID;
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
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
