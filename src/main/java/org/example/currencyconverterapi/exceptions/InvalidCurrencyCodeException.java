package org.example.currencyconverterapi.exceptions;

import static java.lang.String.format;

public class InvalidCurrencyCodeException extends RuntimeException{
    public InvalidCurrencyCodeException(String currencyCode) {
        super(format("The provided currency code %s is invalid.", currencyCode));
    }
}
