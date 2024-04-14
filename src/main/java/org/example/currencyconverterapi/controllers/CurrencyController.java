package org.example.currencyconverterapi.controllers;

import jakarta.validation.Valid;
import org.example.currencyconverterapi.exceptions.InvalidCurrencyCodeException;
import org.example.currencyconverterapi.exceptions.InvalidRequestException;
import org.example.currencyconverterapi.mappers.ConversionMapper;
import org.example.currencyconverterapi.mappers.CurrencyMapper;
import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.output_dto.ConversionOutputDto;
import org.example.currencyconverterapi.models.output_dto.ExchangeRateOutputDto;
import org.example.currencyconverterapi.models.SourceCurrencyAmountDto;
import org.example.currencyconverterapi.services.contracts.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

@RestController
@RequestMapping("api/currency-converter")
public class CurrencyController {

    private final CurrencyMapper currencyMapper;
    private final ConversionMapper conversionMapper;
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyMapper currencyMapper,
                              ConversionMapper conversionMapper,
                              CurrencyService currencyService) {
        this.currencyMapper = currencyMapper;
        this.conversionMapper = conversionMapper;
        this.currencyService = currencyService;
    }

    @GetMapping("/exchange-rate")
    public ResponseEntity<?> getExchangeRate(@RequestParam String sourceCurrency,
                                             @RequestParam String targetCurrency) {
        try {
            Currency source = currencyMapper.toCurrencyObj(sourceCurrency);
            Currency target = currencyMapper.toCurrencyObj(targetCurrency);
            ExchangeRateOutputDto exchangeRate = currencyService.getExchangeRate(source, target);
            return ResponseEntity.status(HttpStatus.OK).body(exchangeRate);
        } catch (InvalidCurrencyCodeException | InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/conversions")
    public ResponseEntity<?> createConversion(@RequestParam String sourceCurrency,
                                              @RequestParam String targetCurrency,
                                              @RequestBody @Valid SourceCurrencyAmountDto amountDto) {

        try {
            Conversion conversion = conversionMapper.toObj(sourceCurrency, targetCurrency);
            currencyService.createConversionAmount(conversion, amountDto.getAmount());
            ConversionOutputDto outputDto = conversionMapper.toOutputDto(conversion);
            return ResponseEntity.status(HttpStatus.OK).body(outputDto);
        } catch (InvalidCurrencyCodeException | InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
