package org.example.currencyconverterapi.mappers;

import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.output_dto.ConversionOutputDto;
import org.example.currencyconverterapi.models.output_dto.ConversionOutputPageDto;
import org.example.currencyconverterapi.models.output_dto.ExchangeRateOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConversionMapper {

    private final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;
    private final CurrencyMapper currencyMapper;

    @Autowired
    public ConversionMapper(CurrencyMapper currencyMapper) {
        this.currencyMapper = currencyMapper;
    }

    public Conversion toObj(String sourceCurrency, String targetCurrency) {
        Conversion conversion = new Conversion();
        conversion.setSourceCurrency(currencyMapper.toCurrencyObj(sourceCurrency).getCurrencyCode());
        conversion.setTargetCurrency(currencyMapper.toCurrencyObj(targetCurrency).getCurrencyCode());
        return conversion;
    }

    public ConversionOutputDto toOutputDto(Conversion conversion) {
        return new ConversionOutputDto(roundAmount(conversion.getTargetCurrency(), conversion.getAmount()),
                conversion.getTimeStamp(),
                conversion.getUUID());
    }

    public ExchangeRateOutputDto toOutputDto(double amount, String targetCurrencyCode) {
        return new ExchangeRateOutputDto(amount, targetCurrencyCode);
    }

    public ConversionOutputPageDto toConversionPageDto (Page<Conversion> conversions) {
        List<ConversionOutputDto> outputConversions = new ArrayList<>();
        ConversionOutputPageDto conversionPage = new ConversionOutputPageDto();
        for (Conversion conversion : conversions) {
            outputConversions.add(toOutputDto(conversion));
        }
        conversionPage.setConversionList(outputConversions);
        conversionPage.setCurrentPage(conversions.
                getPageable().getPageNumber() + 1);
        conversionPage.setTotalPages(conversions.getTotalPages());
        return conversionPage;
    }

    private BigDecimal roundAmount(String currencyCode, BigDecimal amount) {
        return amount.setScale(currencyMapper.toCurrencyObj
                (currencyCode).getDefaultFractionDigits(), DEFAULT_ROUNDING);
    }
}
