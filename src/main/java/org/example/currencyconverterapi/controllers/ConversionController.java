package org.example.currencyconverterapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.currencyconverterapi.exceptions.EntityNotFoundException;
import org.example.currencyconverterapi.exceptions.InvalidCurrencyCodeException;
import org.example.currencyconverterapi.exceptions.InvalidRequestException;
import org.example.currencyconverterapi.exceptions.UnsuccessfulResponseException;
import org.example.currencyconverterapi.mappers.ConversionMapper;
import org.example.currencyconverterapi.mappers.CurrencyMapper;
import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.input_dto.ConversionFilterOptions;
import org.example.currencyconverterapi.models.output_dto.ConversionOutputDto;
import org.example.currencyconverterapi.models.output_dto.ConversionOutputPageDto;
import org.example.currencyconverterapi.models.output_dto.ExchangeRateOutputDto;
import org.example.currencyconverterapi.models.input_dto.SourceCurrencyAmountDto;
import org.example.currencyconverterapi.services.contracts.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Currency;

@RestController
@RequestMapping("/api/currency-converter")
public class ConversionController {

    private final CurrencyMapper currencyMapper;
    private final ConversionMapper conversionMapper;
    private final ConversionService conversionService;

    @Autowired
    public ConversionController(CurrencyMapper currencyMapper,
                                ConversionMapper conversionMapper,
                                ConversionService conversionService) {
        this.currencyMapper = currencyMapper;
        this.conversionMapper = conversionMapper;
        this.conversionService = conversionService;
    }

    @GetMapping("/exchange-rate")
    @Operation(
            summary = "Retrieve Latest Exchange Rate",
            description = "Returns the latest exchange rate for target currency of the provided currency pair." +
                    "The request requires two parameters, first of which is the source currency code," +
                    " followed by target currency code.",
            parameters = {
                    @Parameter(
                            name = "sourceCurrency",
                            description = "The currency code belonging to the source currency, " +
                                    "based on which the exchange rate will be processed.",
                            required = true,
                            example = "DKK"
                    ),
                    @Parameter(
                            name = "targetCurrency",
                            description = "The currency code belonging to the target currency," +
                                    " based on which the exchange rate will be obtained.",
                            required = true,
                            example = "BGN"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful retrieval of latest exchange rate.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExchangeRateOutputDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid currency. This response denotes that either of the provided" +
                                    "currency codes does not belong to ISO" +
                                    " (International Organization for Standardization) 4217."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "The request could not be processed. This response occurs when" +
                                    "the requested exchange rate could not be obtained."
                    )
            }
    )
    public ResponseEntity<ExchangeRateOutputDto> getExchangeRate(@RequestParam String sourceCurrency,
                                                                 @RequestParam String targetCurrency) {
        try {
            Currency source = currencyMapper.toCurrencyObj(sourceCurrency);
            Currency target = currencyMapper.toCurrencyObj(targetCurrency);
            double exchangeRate = conversionService.getExchangeRate(source, target);
            ExchangeRateOutputDto outputDto =
                    conversionMapper.toOutputDto(exchangeRate, targetCurrency);
            return ResponseEntity.ok(outputDto);
        } catch (InvalidCurrencyCodeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnsuccessfulResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/conversions")
    @Operation(
            summary = "Create Conversion for Currency Pair",
            description = "Returns a converted amount for target currency of the provided currency pair." +
                    "The request requires three parameters first of which is the source currency code," +
                    "followed by target currency code, and lastly an amount in the target currency," +
                    " which has to be at least 1 and not a negative number.",
            parameters = {
                    @Parameter(
                            name = "sourceCurrency",
                            description = "The currency code belonging to the source currency, " +
                                    "based on which the conversion will be processed.",
                            required = true,
                            example = "DKK"
                    ),
                    @Parameter(
                            name = "targetCurrency",
                            description = "The currency code belonging to the target currency," +
                                    " based on which the conversion will be obtained.",
                            required = true,
                            example = "BGN"
                    ),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful conversion from provided currency pair.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ConversionOutputDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid currency. This response denotes that either of the provided" +
                                    "currency codes does not belong to ISO" +
                                    " (International Organization for Standardization) 4217."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "The request could not be processed. This response occurs when" +
                                    "the requested conversion could not be obtained."
                    )
            }
    )
    public ResponseEntity<Conversion> createConversion(@RequestParam String sourceCurrency,
                                                                @RequestParam String targetCurrency,
                                                                @RequestBody @Valid SourceCurrencyAmountDto amountDto) {

        try {
            Conversion conversion = conversionMapper.toObj(sourceCurrency, targetCurrency);
            conversionService.createConversionAmount(conversion, amountDto.getAmount());
           // ConversionOutputDto outputDto = conversionMapper.toOutputDto(conversion);
            return ResponseEntity.ok(conversion);
        } catch (InvalidCurrencyCodeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UnsuccessfulResponseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/conversions")
    @Operation(
            summary = "Retrieve History of Conversions",
            description = "Retrieves a filtered history of conversions that were logged in the application" +
                    " based on a single criteria. The list of conversions may be filtered either by timeframe(" +
                    "before and after, after or before) or by unique transaction identifier (UUID) for short." +
                    " Optional parameters are page number and page size.",
            parameters = {
                    @Parameter(
                            name = "after",
                            description = "The date and time, after which conversions will be listed if there are such." +
                                    "The date and time format is as follows: yyyy-MM-ddTHH:mm:ss.",
                            example = "2024-04-05T10:44:55"
                    ),
                    @Parameter(
                            name = "before",
                            description = "The date and time, before which conversions will be listed if there are such." +
                                    "The date and time format is as follows: yyyy-MM-ddTHH:mm:ss.",
                            example = "2024-04-20T20:44:55"
                    ),
                    @Parameter(
                            name = "transactionId",
                            description = "The unique transaction identifier of one or more conversions." +
                                    "Such identifier is generated after a successful conversion. It is comprised of both" +
                                    "digits and letters with a fixed length.",
                            example = "a7f4f370-1ee1-468a-a9c7-460763a423c0"
                    ),
                    @Parameter(
                            name = "pageNumber",
                            description = "The number of the conversion page that will be listed if there is such." +
                                    "If left empty the first conversion page will be listed as a result",
                            example = "2"
                    ),
                    @Parameter(
                            name = "pageSize",
                            description = "The size of the conversion page or in other words the number of conversions" +
                                    "that will be listed on a page if specified. If left empty up to 5 conversions will" +
                                    "be listed per page.",
                            example = "10"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful listing of conversion page under provided criteria.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ConversionOutputPageDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Requested resource was not found or does not exist" +
                                    " This response denotes that no conversion or conversions were found under" +
                                    "provided criteria."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No request parameters were provided, both request parameters were used at " +
                                    "the same time, or The provided timeframe is invalid.\n " +
                                    "This response denotes that either one of the above mentioned scenarios" +
                                    "has happened. In such cases go over your request parameters and make a new request" +
                                    "under the required conditions."
                    )
            }
    )
    public ResponseEntity<ConversionOutputPageDto> getConversions(@RequestParam(required = false)
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after,
                                                                  @RequestParam(required = false)
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before,
                                                                  @RequestParam(required = false) String transactionId,
                                                                  @RequestParam(required = false) Integer pageNumber,
                                                                  @RequestParam(required = false) Integer pageSize) {

        try {
            ConversionFilterOptions filterOptions = new ConversionFilterOptions
                    (after, before, transactionId, pageNumber, pageSize);
            Page<Conversion> conversions = conversionService.getAllWithFilter(filterOptions);
            ConversionOutputPageDto output = conversionMapper.toConversionPageDto(conversions);
            return ResponseEntity.ok(output);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

