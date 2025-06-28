package com.sara.redis_task_manager.model;

import lombok.Data;

@Data
public class TaskRequest {
    private String type;
    private String payload;
}
