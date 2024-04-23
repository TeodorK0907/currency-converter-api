package org.example.currencyconverterapi.helpers;

import org.example.currencyconverterapi.exceptions.EntityNotFoundException;
import org.example.currencyconverterapi.exceptions.InvalidRequestException;
import org.example.currencyconverterapi.models.Conversion;
import org.example.currencyconverterapi.models.input_dto.ConversionFilterOptions;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.currencyconverterapi.helpers.ConstantHelper.*;

public class ConversionFilterOptionsValidator {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public static int getPageNum(Optional<Integer> pageNum) {
        if (pageNum.isEmpty()) {
            return DEFAULT_PAGE;
        }
        return pageNum.get() - 1;
    }

    public static int getPageSize(Optional<Integer> pageSize) {
        if (pageSize.isEmpty()) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize.get();
    }

    public static void validateTimeFrame(ConversionFilterOptions filterOptions) {
        if (filterOptions.getAfter().get().isAfter(filterOptions.getBefore().get())) {
            throw new InvalidRequestException(ConversionFilterOptionsErrors.INVALID_TIMEFRAME.toString());
        }
    }

    public static void isFilterOptionsEmpty(ConversionFilterOptions filterOptions) {
        if (filterOptions.getAfter().isEmpty()
                && filterOptions.getBefore().isEmpty()
                && filterOptions.getTransactionId().isEmpty()) {
            throw new InvalidRequestException(ConversionFilterOptionsErrors.NO_REQUEST_PARAM.toString());
        }
    }

    public static void areBothCriteriaUsed(ConversionFilterOptions filterOptions) {
        if ((filterOptions.getAfter().isPresent() || filterOptions.getBefore().isPresent())
                && filterOptions.getTransactionId().isPresent()) {
            throw new InvalidRequestException(ConversionFilterOptionsErrors.INVALID_TIMEFRAME.toString());
        }
    }

    public static void validateIfPageContentIsEmpty(Page<Conversion> conversionPage,
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
            throw new EntityNotFoundException(sb.toString());
        }
    }
}
