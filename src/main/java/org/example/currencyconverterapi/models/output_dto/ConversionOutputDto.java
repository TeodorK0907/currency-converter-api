package org.example.currencyconverterapi.models.output_dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConversionOutputDto {
    private final BigDecimal amount;
    private final LocalDateTime timeStamp;
    private final String UUID;

    public ConversionOutputDto(BigDecimal amount, LocalDateTime timeStamp, String UUID) {
        this.amount = amount;
        this.timeStamp = timeStamp.withNano(0);
        this.UUID = UUID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getUUID() {
        return UUID;
    }
}