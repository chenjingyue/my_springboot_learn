package com.example.my_springboot_learn.redis.cache2;

import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
//@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisAnnotationCacheConfiguration {
    private Duration timeToLive = Duration.ZERO;
    private Duration oneMinute = Duration.ofSeconds(60);
    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(this.timeToLive)
                .entryTtl(this.oneMinute)
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(valueSerializer()))
                .disableCachingNullValues();

        /*RedisCacheConfiguration config1 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.timeToLive)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
                .disableCachingNullValues();
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("name1");
        cacheNames.add("name2");
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("name1", config);
        cacheConfigurations.put("name2", config);*/

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
//                .initialCacheNames(cacheNames)
//                .withInitialCacheConfigurations(cacheConfigurations)
                .cacheDefaults(config)
                .transactionAware()
                .build();

        System.out.println("自定义RedisCacheManager加载完成");
        return redisCacheManager;
    }

    /*@Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());

        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        System.out.println("自定义RedisTemplate加载完成");
        return redisTemplate;
    }*/

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    /*private RedisSerializer<Object> fastJsonRedisSerializer() {
        return new FastJsonRedisSerializer(Object.class);
    }*/

    /*private RedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }*/


}
