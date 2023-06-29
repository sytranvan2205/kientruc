package com.redispubsub.RedisPubSub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;
    
    @Bean
    MessageListenerAdapter messageListener() { 
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }
}
