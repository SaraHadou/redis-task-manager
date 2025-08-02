package com.sara.redis_task_manager.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sara.redis_task_manager.model.TaskRequest;
import com.sara.redis_task_manager.queue.RedisQueueService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskWorker {

    @Autowired
    private RedisQueueService redisQueueService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String[] QUEUE_TYPES = {"email", "sms"};

    @PostConstruct
    public void startWorker() {
        new Thread(() -> {
            System.out.println("Task worker started...");
            while (true) {
                try {
                    for (String type : QUEUE_TYPES) {
                        String queueName = "taskQueue:" + type;
                        String taskJson = redisQueueService.popFromQueue(queueName);
                        if (taskJson != null) {
                            try {
                                TaskRequest task = objectMapper.readValue(taskJson, TaskRequest.class);
                                processTask(task);
                            } catch (Exception e) {
                                System.err.println("Failed to parse/process task from queue: " + queueName);
                                e.printStackTrace();
                            }
                        }
                    }
                    Thread.sleep(2000); // Prevent CPU overuse
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

            }
        }).start();
    }

    private void processTask(TaskRequest task) {
        System.out.println("Processing " + task.getType().toUpperCase() + " task: " + task.getPayload());
    }

}
