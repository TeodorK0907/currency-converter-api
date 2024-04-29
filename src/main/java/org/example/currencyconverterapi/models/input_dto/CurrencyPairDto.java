package org.example.currencyconverterapi.models.input_dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Currency;
import java.util.Objects;


public class CurrencyPairDto implements Comparable<CurrencyPairDto> {

    private static final int DEFAULT_TEMPORAL_UNIT = 0;
    private static final int ZERO = 0;
    private Currency source;
    private Currency target;
    private final LocalDateTime timestamp;

    public CurrencyPairDto() {
        this.timestamp = LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                DEFAULT_TEMPORAL_UNIT,
                DEFAULT_TEMPORAL_UNIT
        );
    }

    public Currency getSource() {
        return source;
    }

    public void setSource(Currency source) {
        this.source = source;
    }

    public Currency getTarget() {
        return target;
    }

    public void setTarget(Currency target) {
        this.target = target;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyPairDto that)) return false;
        return Objects.equals(source, that.source)
                && Objects.equals(target, that.target)
                && (this.compareTo((CurrencyPairDto) o) == ZERO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public int compareTo(CurrencyPairDto pair) {
        if (this.timestamp.getYear() - pair.getTimestamp().getYear() == ZERO) {
            if (this.timestamp.getMonth().getValue() - pair.getTimestamp().getMonth().getValue() == ZERO) {
                if (this.timestamp.getDayOfMonth() - pair.getTimestamp().getDayOfMonth() == ZERO) {
                    if (this.timestamp.getHour() - pair.getTimestamp().getHour() == ZERO) {
                        return 0;
                    }
                }
            }
        }
        return -1;
    }
}
