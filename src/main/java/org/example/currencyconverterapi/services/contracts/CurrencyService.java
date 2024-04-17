package org.example.currencyconverterapi.services.contracts;

import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.input_dto.ConversionFilterOptions;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyService {

    double getExchangeRate(Currency source, Currency target);

    void createConversionAmount(Conversion conversion, BigDecimal amount);

    Page<Conversion> getAllWithFilter(ConversionFilterOptions filterOptions);
}
