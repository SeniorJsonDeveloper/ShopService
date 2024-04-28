package ru.shopservice.configuration;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Component
@ConfigurationProperties(prefix = "app.cache")
public class CacheProperties {

    private final List<String> cacheNames = new ArrayList<>();

    private final Map<String,AppCacheProperties> caches = new HashMap<>();

    private CacheType cacheType;

    @Data
    public static class AppCacheProperties{
        private Duration expiry = Duration.ZERO;
    }

    public interface CacheNames {
        String ORDER_BY_ID = "orderById";
        String ORDER_BY_FILTER = "orderFilter";
        String ORDERS = "orders";
        String NEW_ORDER = "newOrder";

    }

    public enum CacheType{
        IN_MEMORY,REDIS
    }
}
