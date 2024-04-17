package org.example.currencyconverterapi.models.input_dto;

import java.time.LocalDateTime;
import java.util.Optional;

public class ConversionFilterOptions {

    private Optional<LocalDateTime> after;
    private Optional<LocalDateTime> before;
    private Optional<String> transactionId;
    private Optional<Integer> pageNumber;
    private Optional<Integer> pageSize;

    public ConversionFilterOptions(LocalDateTime after,
                                   LocalDateTime before,
                                   String transactionId,
                                   Integer pageNumber,
                                   Integer pageSize) {
        this.after = Optional.ofNullable(after);
        this.before = Optional.ofNullable(before);
        this.transactionId = Optional.ofNullable(transactionId);
        this.pageNumber = Optional.ofNullable(pageNumber);
        this.pageSize = Optional.ofNullable(pageSize);
    }

    public Optional<LocalDateTime> getAfter() {
        return after;
    }

    public void setAfter(Optional<LocalDateTime> after) {
        this.after = after;
    }

    public Optional<LocalDateTime> getBefore() {
        return before;
    }

    public void setBefore(Optional<LocalDateTime> before) {
        this.before = before;
    }

    public Optional<String> getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Optional<String> transactionId) {
        this.transactionId = transactionId;
    }

    public Optional<Integer> getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Optional<Integer> pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Optional<Integer> getPageSize() {
        return pageSize;
    }

    public void setPageSize(Optional<Integer> pageSize) {
        this.pageSize = pageSize;
    }
}
