package org.example.currencyconverterapi.helpers;

import org.example.currencyconverterapi.models.input_dto.CurrencyPairDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ExchangeRateCacheManager {

    private final ConcurrentHashMap<CurrencyPairDto, Double> cache = new ConcurrentHashMap<>();

    public ConcurrentHashMap<CurrencyPairDto, Double> getCache() {
        return new ConcurrentHashMap<>(cache);
    }
    public double get(CurrencyPairDto pair) {
       return getCache().get(pair);
    }

    public void put(CurrencyPairDto key, Double value) {
        cache.put(key, value);
    }

    public Double putIfAbsent(CurrencyPairDto key, Double value) {
        cache.putIfAbsent(key, value);
        return value;
    }

    public boolean contains(CurrencyPairDto pair) {
        return cache.containsKey(pair);
    }

}
