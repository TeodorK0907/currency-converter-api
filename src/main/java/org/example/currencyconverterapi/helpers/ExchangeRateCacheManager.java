package org.example.currencyconverterapi.helpers;

import org.example.currencyconverterapi.models.input_dto.CurrencyPairDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ExchangeRateCacheManager {

    private final ConcurrentHashMap<Integer, ConcurrentHashMap<CurrencyPairDto, Double>> cache = new ConcurrentHashMap<>();
    private Integer latestExchangeRateKey = LocalTime.now().getHour();
    private LocalDateTime lastRefreshTimeStamp = LocalDateTime.now();

    public ConcurrentHashMap<Integer, ConcurrentHashMap<CurrencyPairDto, Double>> getCache() {
        return new ConcurrentHashMap<>(cache);
    }
    public double get(CurrencyPairDto pair) {
       return getCache().get(getLatestExchangeRateKey()).get(pair);
    }

    public void put(CurrencyPairDto nestedKey, Double value) {
        cache.get(getLatestExchangeRateKey()).put(nestedKey, value);
    }

    public Double putIfAbsent(Integer key, CurrencyPairDto nestedKey, Double value) {
        cache.get(key).put(nestedKey, value);
        return value;
    }

    public boolean contains(CurrencyPairDto pair) {
        if (cache.isEmpty()) {
            cache.put(getLatestExchangeRateKey(), new ConcurrentHashMap<>());
            return false;
        }
        return cache.get(getLatestExchangeRateKey()).containsKey(pair);
    }

    public void cleanCache() {
        cache.remove(getLatestExchangeRateKey());
    }

    public Integer getLatestExchangeRateKey() {
        return latestExchangeRateKey;
    }

    public void setLatestExchangeRateKey(Integer latestExchangeRateKey) {
        this.latestExchangeRateKey = latestExchangeRateKey;
    }

    public LocalDateTime getLastRefreshTimeStamp() {
        return lastRefreshTimeStamp;
    }

    public void setLastRefreshTimeStamp(LocalDateTime lastRefreshTimeStamp) {
        this.lastRefreshTimeStamp = lastRefreshTimeStamp;
    }
}
