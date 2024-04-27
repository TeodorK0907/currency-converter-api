package org.example.currencyconverterapi.services;

import org.example.currencyconverterapi.clients.contracts.CurrencyBeaconClient;
import org.example.currencyconverterapi.exceptions.UnsuccessfulResponseException;
import org.example.currencyconverterapi.helpers.filter_options_helper.ConversionFilterOptionsValidator;
import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.input_dto.ConversionFilterOptions;
import org.example.currencyconverterapi.repositories.ConversionRepository;
import org.example.currencyconverterapi.services.contracts.ConversionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.currencyconverterapi.helpers.ConstantHelper.*;

@Service
public class ConversionServiceImpl implements ConversionService {

    private final String RESPONSE_KEY_VALUE = "value";
    private final String RESPONSE_KEY_RATES = "rates";
    private final String RESPONSE_KEY_META = "meta";
    private final String RESPONSE_KEY_RESPONSE = "response";
    private final String RESPONSE_KEY_CODE = "code";

    private final ConversionRepository conversionRepository;
    private final CurrencyBeaconClient client;

    @Autowired
    public ConversionServiceImpl(ConversionRepository conversionRepository,
                                 CurrencyBeaconClient client) {
        this.conversionRepository = conversionRepository;
        this.client = client;
    }

    @Override
    public double getExchangeRate(Currency source, Currency target) {
        JSONObject responseJSON = new JSONObject
                (client.getExchangeRateResponse(source.getCurrencyCode()));
        JSONObject responseCode = responseJSON.getJSONObject(RESPONSE_KEY_META);
        if (responseCode.getInt(RESPONSE_KEY_CODE) == CURRENCY_BEACON_SUCCESS) {
            JSONObject response = responseJSON.getJSONObject(RESPONSE_KEY_RESPONSE);
            JSONObject exchangeRateJson = response.getJSONObject(RESPONSE_KEY_RATES);
            return exchangeRateJson.getDouble(target.getCurrencyCode());
        }
        throw new UnsuccessfulResponseException(COULD_NOT_PROCESS_REQUEST_ERROR_MESSAGE);
    }


    @Override
    public void createConversionAmount(Conversion conversion, BigDecimal amount) {
        JSONObject responseJSON = new JSONObject
                (client.getConversion(conversion, amount));
        JSONObject responseCode = responseJSON.getJSONObject(RESPONSE_KEY_META);
        if (responseCode.getInt(RESPONSE_KEY_CODE) == CURRENCY_BEACON_SUCCESS) {
            JSONObject response = responseJSON.getJSONObject(RESPONSE_KEY_RESPONSE);
            processConversion(conversion, response);
            conversionRepository.save(conversion);
            return;
        }
        throw new UnsuccessfulResponseException(COULD_NOT_PROCESS_REQUEST_ERROR_MESSAGE);
    }

    @Override
    public Page<Conversion> getAllWithFilter(ConversionFilterOptions filterOptions) {
        ConversionFilterOptionsValidator.isFilterOptionsEmpty(filterOptions);
        ConversionFilterOptionsValidator.areBothCriteriaUsed(filterOptions);
        Page<Conversion> conversionPage;
        Pageable page = PageRequest.of(ConversionFilterOptionsValidator.getPageNum(filterOptions.getPageNumber()),
                ConversionFilterOptionsValidator.getPageSize(filterOptions.getPageSize()));
        if (filterOptions.getBefore().isPresent() && filterOptions.getAfter().isPresent()) {
            ConversionFilterOptionsValidator.validateTimeFrame(filterOptions);
            conversionPage = conversionRepository.
                    findAllByTimeStampIsAfterAndTimeStampIsBeforeOrderByTimeStamp(
                            filterOptions.getAfter().get(),
                            filterOptions.getBefore().get(),
                            page
                    );
        } else if (filterOptions.getBefore().isPresent()) {
            conversionPage = conversionRepository.findAllByTimeStampBeforeOrderByTimeStamp(
                    filterOptions.getBefore().get(),
                    page);
        } else if (filterOptions.getAfter().isPresent()) {
            conversionPage = conversionRepository.findAllByTimeStampAfterOrderByTimeStamp(
                    filterOptions.getAfter().get(),
                    page);
        } else {
            conversionPage = conversionRepository.findAllByUUID(
                    filterOptions.getTransactionId().get(),
                    page);
        }
        ConversionFilterOptionsValidator.validateIfPageContentIsEmpty(conversionPage, filterOptions);
        return conversionPage;
    }

    private void processConversion(Conversion conversion, JSONObject response) {
        conversion.setTimeStamp(LocalDateTime.now());
        conversion.setAmount(BigDecimal.valueOf(response.getDouble(RESPONSE_KEY_VALUE)));
    }
}