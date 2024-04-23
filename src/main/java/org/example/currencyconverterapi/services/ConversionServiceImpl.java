package org.example.currencyconverterapi.services;

import org.example.currencyconverterapi.configurations.WebClientConfig;
import org.example.currencyconverterapi.exceptions.EntityNotFoundException;
import org.example.currencyconverterapi.exceptions.InvalidRequestException;
import org.example.currencyconverterapi.exceptions.UnsuccessfulResponseException;
import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.input_dto.ConversionFilterOptions;
import org.example.currencyconverterapi.repositories.ConversionRepository;
import org.example.currencyconverterapi.services.contracts.ConversionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.currencyconverterapi.helpers.ConstantHelper.*;

@Service
public class ConversionServiceImpl implements ConversionService {

    private final String INVALID_TIMEFRAME_ERROR_MESSAGE = "The provided timeframe is invalid";
    private final String NO_REQUEST_PARAM_ERROR_MESSAGE =
            "Please provide either a transaction date or transaction identifier.";
    private final String BOTH_FILTER_CRITERIA_USED_ERROR_MESSAGE = "You may filter either by transaction date" +
            " or transaction identifier, not both";
    private final int DEFAULT_PAGE = 0;
    private final int DEFAULT_PAGE_SIZE = 5;
    private final String API_KEY_REQUEST_PARAM = "api_key";
    private final String BASE_CURRENCY_REQUEST_PARAM = "base";
    private final String FROM_REQUEST_PARAM = "from";
    private final String TO_REQUEST_PARAM = "to";
    private final String AMOUNT_REQUEST_PARAM = "amount";
    private final String RESPONSE_KEY_VALUE = "value";
    private final String RESPONSE_KEY_RATES = "rates";
    private final String RESPONSE_KEY_META = "meta";
    private final String RESPONSE_KEY_RESPONSE = "response";
    private final String RESPONSE_KEY_CODE = "code";
    private final String EXCHANGE_RATE_ENDPOINT = "/latest";
    private final String CONVERT_ENDPOINT = "/convert";

    @Value("${currency.beacon.api-key}")
    private String key;

    private final ConversionRepository conversionRepository;

    private final WebClientConfig webClient;

    @Autowired
    public ConversionServiceImpl(ConversionRepository conversionRepository,
                                 WebClientConfig webClient) {
        this.conversionRepository = conversionRepository;
        this.webClient = webClient;
    }

    @Override
    public double getExchangeRate(Currency source, Currency target) {
        JSONObject responseJSON = new JSONObject
                (getExchangeRateResponse(source.getCurrencyCode()));
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
                (getConversionResponse(conversion, amount));
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
        isFilterOptionsEmpty(filterOptions);
        areBothCriteriaUsed(filterOptions);
        Page<Conversion> conversionPage;
        Pageable page = PageRequest.of(getPageNum(filterOptions.getPageNumber()),
                getPageSize(filterOptions.getPageSize()));
        if (filterOptions.getBefore().isPresent() && filterOptions.getAfter().isPresent()) {
            validateTimeFrame(filterOptions);
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
        validateIfPageContentIsEmpty(conversionPage, filterOptions);
        return conversionPage;
    }

    private void processConversion(Conversion conversion, JSONObject response) {
        conversion.setTimeStamp(LocalDateTime.now());
        conversion.setAmount(BigDecimal.valueOf(response.getDouble(RESPONSE_KEY_VALUE)));
    }

    private String getConversionResponse(Conversion conversion, BigDecimal amount) {

        return webClient.webClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path(CONVERT_ENDPOINT)
                        .queryParam(API_KEY_REQUEST_PARAM, key)
                        .queryParam(FROM_REQUEST_PARAM, conversion.getSourceCurrency())
                        .queryParam(TO_REQUEST_PARAM, conversion.getTargetCurrency())
                        .queryParam(AMOUNT_REQUEST_PARAM, amount.toBigInteger())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String getExchangeRateResponse(String sourceCurrencyCode) {
        WebClient client = webClient.webClient();
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(EXCHANGE_RATE_ENDPOINT)
                        .queryParam(API_KEY_REQUEST_PARAM, key)
                        .queryParam(BASE_CURRENCY_REQUEST_PARAM, sourceCurrencyCode)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private int getPageNum(Optional<Integer> pageNum) {
        if (pageNum.isEmpty()) {
            return DEFAULT_PAGE;
        }
        return pageNum.get() - 1;
    }

    private int getPageSize(Optional<Integer> pageSize) {
        if (pageSize.isEmpty()) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize.get();
    }

    private void validateTimeFrame(ConversionFilterOptions filterOptions) {
        if (filterOptions.getAfter().get().isAfter(filterOptions.getBefore().get())) {
            throw new InvalidRequestException(INVALID_TIMEFRAME_ERROR_MESSAGE);
        }
    }

    private void isFilterOptionsEmpty(ConversionFilterOptions filterOptions) {
        if (filterOptions.getAfter().isEmpty()
                && filterOptions.getBefore().isEmpty()
                && filterOptions.getTransactionId().isEmpty()) {
            throw new InvalidRequestException(NO_REQUEST_PARAM_ERROR_MESSAGE);
        }
    }

    private void areBothCriteriaUsed(ConversionFilterOptions filterOptions) {
        if ((filterOptions.getAfter().isPresent() || filterOptions.getBefore().isPresent())
                && filterOptions.getTransactionId().isPresent()) {
            throw new InvalidRequestException(BOTH_FILTER_CRITERIA_USED_ERROR_MESSAGE);
        }
    }

    private void validateIfPageContentIsEmpty(Page<Conversion> conversionPage,
                                              ConversionFilterOptions filterOptions) {
        if (conversionPage.getContent().isEmpty()) {
            StringBuilder sb = new StringBuilder("No conversions with ");
            List<String> params = new ArrayList<>();

            filterOptions.getAfter().ifPresent(param -> {
                if (!param.toString().isBlank()) {
                    params.add(String.format(ENTITY_NOT_FOUND_AFTER_TIMESTAMP_BASE_MESSAGE, param));
                }
            });

            filterOptions.getBefore().ifPresent(param -> {
                if (!param.toString().isBlank()) {
                    params.add(String.format(ENTITY_NOT_FOUND_BEFORE_TIMESTAMP_BASE_MESSAGE, param));
                }
            });

            filterOptions.getTransactionId().ifPresent(param -> {
                if (!param.isBlank()) {
                    params.add(String.format(ENTITY_NOT_FOUND_TRANSACTION_ID_BASE_MESSAGE, param));
                }
            });

            filterOptions.getPageNumber().ifPresent(param -> {
                if (!param.toString().isBlank()) {
                    params.add(String.format(ENTITY_NOT_FOUND_PAGE_NUM_BASE_MESSAGE, param));
                }
            });
            sb.append(String.join(", ", params));
            sb.append(ENTITY_NOT_FOUND_ENDING_MESSAGE);
            System.out.println(sb);
            throw new EntityNotFoundException(sb.toString());
        }
    }
}
