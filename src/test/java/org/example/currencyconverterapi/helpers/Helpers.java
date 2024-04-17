package org.example.currencyconverterapi.helpers;

import org.example.currencyconverterapi.models.Conversion;


import java.util.Currency;

public class Helpers {

    public static Conversion createMockConversion() {
        Conversion mockConversion = new Conversion();
        mockConversion.setSourceCurrency("BGN");
        mockConversion.setTargetCurrency("USD");
        return mockConversion;
    }

    public static Currency createMockCurrency() {
        return Currency.getInstance("BGN");
    }

    public static Currency createAnotherMockCurrency() {
        return Currency.getInstance("USD");
    }

    }
