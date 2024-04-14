package org.example.currencyconverterapi.services;

import org.example.currencyconverterapi.configurations.WebClientConfig;
import org.example.currencyconverterapi.exceptions.InvalidRequestException;
import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.output_dto.ExchangeRateOutputDto;
import org.example.currencyconverterapi.repositories.CurrencyRepository;
import org.example.currencyconverterapi.services.contracts.CurrencyService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.example.currencyconverterapi.helpers.ConstantHelper.COULD_NOT_PROCESS_REQUEST_ERROR_MESSAGE;
import static org.example.currencyconverterapi.helpers.ConstantHelper.CURRENCY_BEACON_SUCCESS;

@Service
public class CurrencyServiceImpl implements CurrencyService {

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

    private final CurrencyRepository currencyRepository;

    private final WebClientConfig webClient;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository,
                               WebClientConfig webClient) {
        this.currencyRepository = currencyRepository;
        this.webClient = webClient;
    }

    @Override
    public ExchangeRateOutputDto getExchangeRate(Currency source, Currency target) {
        JSONObject responseJSON = new JSONObject
                (getExchangeRateResponse(source.getCurrencyCode()));
        JSONObject responseCode = responseJSON.getJSONObject(RESPONSE_KEY_META);
        if (responseCode.getInt(RESPONSE_KEY_CODE) == CURRENCY_BEACON_SUCCESS) {
            JSONObject response = responseJSON.getJSONObject(RESPONSE_KEY_RESPONSE);
            return getCurrencyExchangeRate(target, response);
        }
        throw new InvalidRequestException(COULD_NOT_PROCESS_REQUEST_ERROR_MESSAGE);
    }

    @Override
    public void createConversionAmount(Conversion conversion, BigDecimal amount) {
        JSONObject responseJSON = new JSONObject
                (getConversionResponse(conversion, amount));
        JSONObject responseCode = responseJSON.getJSONObject(RESPONSE_KEY_META);
        if (responseCode.getInt(RESPONSE_KEY_CODE) == CURRENCY_BEACON_SUCCESS) {
            JSONObject response = responseJSON.getJSONObject(RESPONSE_KEY_RESPONSE);
            processConversion(conversion, response);
            return;
        }
        throw new InvalidRequestException(COULD_NOT_PROCESS_REQUEST_ERROR_MESSAGE);
    }

    private void processConversion(Conversion conversion, JSONObject response) {
        conversion.setTimeStamp(LocalDateTime.now());
        conversion.setAmount(BigDecimal.valueOf(response.getDouble(RESPONSE_KEY_VALUE)),
                conversion.getTargetCurrency());
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
        return webClient.webClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path(EXCHANGE_RATE_ENDPOINT)
                        .queryParam(API_KEY_REQUEST_PARAM, key)
                        .queryParam(BASE_CURRENCY_REQUEST_PARAM, sourceCurrencyCode)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private ExchangeRateOutputDto getCurrencyExchangeRate(Currency target, JSONObject responseJson) {
        JSONObject exchangeRateJson = responseJson.getJSONObject(RESPONSE_KEY_RATES);
        double exchangeRate = exchangeRateJson.getDouble(target.getCurrencyCode());
        return new ExchangeRateOutputDto(exchangeRate, target.getCurrencyCode());
    }
}
