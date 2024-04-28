package ru.shopservice.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app.redis",name = "enable",havingValue = "true")
    @ConditionalOnExpression("'${app.cache.cacheType}'.equals('redis')")
    public CacheManager redisCacheManager(CacheProperties cacheProperties, LettuceConnectionFactory factory){
        var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
        Map<String,RedisCacheConfiguration> redisMap = new HashMap<>();
        cacheProperties.getCacheNames().forEach(cacheName -> {
            redisMap.put(cacheName,RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(cacheProperties.getCaches().get(cacheName).getExpiry()));
        });
        return RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(redisMap)
                .build();
    }
}
