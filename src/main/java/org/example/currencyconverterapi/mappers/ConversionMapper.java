package org.example.currencyconverterapi.mappers;

import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.output_dto.ConversionOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionMapper {
    private final CurrencyMapper currencyMapper;

    @Autowired
    public ConversionMapper(CurrencyMapper currencyMapper) {
        this.currencyMapper = currencyMapper;
    }

    public Conversion toObj(String sourceCurrency, String targetCurrency) {
        Conversion conversion = new Conversion();
        conversion.setSourceCurrency(currencyMapper.toCurrencyObj(sourceCurrency));
        conversion.setTargetCurrency(currencyMapper.toCurrencyObj(targetCurrency));
        return conversion;
    }

    public ConversionOutputDto toOutputDto(Conversion conversion) {
        return new ConversionOutputDto(conversion.getAmount(),
                conversion.getTimeStamp(),
                conversion.getUUID());
    }
}
