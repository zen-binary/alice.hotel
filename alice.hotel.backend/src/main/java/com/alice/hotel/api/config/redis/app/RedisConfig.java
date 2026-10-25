package com.alice.hotel.api.config.redis.app;

/**
 * Redis config temporarily disabled (SSO/Redis off).
 * To restore: uncomment redis deps in build.gradle, remove spring.autoconfigure.exclude
 * entries for Redis/Session in application.properties, and restore the previous Lettuce-based beans.
 */
//@Configuration
//@Profile("redis")
public class RedisConfig {

    // Previously: LettuceConnectionFactory + StringRedisTemplate beans for SSO token storage.

}
