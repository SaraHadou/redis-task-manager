package com.sara.redis_task_manager.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisQueueService {

    @Autowired
    private final StringRedisTemplate redisTemplate;

    public RedisQueueService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void pushToQueue(String queueName, String value) {
        redisTemplate.opsForList().leftPush(queueName, value);
    }

    public String popFromQueue(String queueName) {
        return redisTemplate.opsForList().rightPop(queueName);
    }

}
