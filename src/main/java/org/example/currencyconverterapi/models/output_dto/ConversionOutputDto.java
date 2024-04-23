package org.example.currencyconverterapi.models.output_dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConversionOutputDto {

    private static final int ZERO_NANO = 0;
    private final int id;
    private final BigDecimal amount;
    private final LocalDateTime timeStamp;
    private final String UUID;

    public ConversionOutputDto(int id, BigDecimal amount, LocalDateTime timeStamp, String UUID) {
        this.id = id;
        this.amount = amount;
        this.timeStamp = timeStamp.withNano(ZERO_NANO);
        this.UUID = UUID;
    }

    public int getId() {
        return id;
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
