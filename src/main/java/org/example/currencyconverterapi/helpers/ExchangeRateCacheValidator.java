package org.example.currencyconverterapi.helpers;

import java.time.LocalDateTime;

public class ExchangeRateCacheValidator {
    private static final int ZERO = 0;

    public static boolean isCacheExpired(ExchangeRateCacheManager cache) {
        LocalDateTime now = LocalDateTime.now();
        if (cache.getLastRefreshTimeStamp().getYear() - now.getYear() == ZERO) {
            if (cache.getLastRefreshTimeStamp().getMonth().getValue() - now.getMonth().getValue() == ZERO) {
                if (cache.getLastRefreshTimeStamp().getDayOfMonth() - now.getDayOfMonth() == ZERO) {
                    if (cache.getLastRefreshTimeStamp().getHour() - now.getHour() == ZERO) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void refreshCache(ExchangeRateCacheManager cache) {
        LocalDateTime now = LocalDateTime.now();
        cache.cleanCache();
        cache.setLastRefreshTimeStamp(now);
        cache.setLatestExchangeRateKey(cache.getLastRefreshTimeStamp().getHour());
    }
}
