package org.example.currencyconverterapi.models.input_dto;


import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;


public class CurrencyPairDto {
    private Currency source;
    private Currency target;

    public CurrencyPairDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyPairDto that)) return false;
        return Objects.equals(source, that.source)
                && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

}
