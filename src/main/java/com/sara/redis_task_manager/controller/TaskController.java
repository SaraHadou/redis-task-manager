package com.sara.redis_task_manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sara.redis_task_manager.model.TaskRequest;
import com.sara.redis_task_manager.queue.RedisQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private RedisQueueService redisQueueService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public String submitTask(@RequestBody TaskRequest taskRequest) throws JsonProcessingException {
        String taskJson = objectMapper.writeValueAsString(taskRequest);
        String queueName = "taskQueue:" + taskRequest.getType().toLowerCase();
        redisQueueService.pushToQueue(queueName, taskJson);
        return "Task submitted successfully to queue: " + queueName;
    }

}
