package com.project.fitness.dto;

import com.project.fitness.model.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityResponse {

    private String id;
    private String userId;
    private ActivityType activityType;
    private Map<String, Object> additionalMetrics;

    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

}
