package org.example.currencyconverterapi.helpers.filter_options_helper;

public enum ConversionFilterOptionsErrors {
    INVALID_TIMEFRAME("The provided timeframe is invalid."),
    NO_REQUEST_PARAM("Please provide either a transaction date or transaction identifier."),
    BOTH_CRITERION_USED("You may filter either by transaction date" +
            " or transaction identifier, not both.");

    private final String description;

    ConversionFilterOptionsErrors(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
