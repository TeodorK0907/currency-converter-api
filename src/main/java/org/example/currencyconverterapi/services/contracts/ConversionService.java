package org.example.currencyconverterapi.services.contracts;

import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.input_dto.ConversionFilterOptions;
import org.example.currencyconverterapi.models.input_dto.CurrencyPairDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ConversionService {

    double getExchangeRate(CurrencyPairDto pair);

    void createConversionAmount(Conversion conversion, BigDecimal amount);

    Page<Conversion> getAllWithFilter(ConversionFilterOptions filterOptions);
}
