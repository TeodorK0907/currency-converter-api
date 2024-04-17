package org.example.currencyconverterapi.exceptions;

public class UnsuccessfulResponseException extends RuntimeException{
    public UnsuccessfulResponseException(String message) {
        super(message);
    }
}
