package com.project.fitness.service;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ResponseEntity<ActivityResponse> create(ActivityRequest activityRequest) {

        String userId = activityRequest.getUserId();

        if(userId == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        try{
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("invalid user id "+ activityRequest.getUserId()));

            Activity activity = Activity.builder()
                    .user(user)
                    .activityType(activityRequest.getActivityType())
                    .additionalMetrics(activityRequest.getAdditionalMetrics())
                    .duration(activityRequest.getDuration())
                    .caloriesBurned(activityRequest.getCaloriesBurned())
                    .startTime(activityRequest.getStartTime())
                    .build();
            Activity savedActivity = activityRepository.save(activity);
            return ResponseEntity.ok(mapToResponse(savedActivity));
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse activityResponse = new ActivityResponse();

        activityResponse.setId(savedActivity.getId());
        activityResponse.setUserId(savedActivity.getUser().getId());
        activityResponse.setActivityType(savedActivity.getActivityType());
        activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        activityResponse.setDuration(savedActivity.getDuration());
        activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
        activityResponse.setStartTime(savedActivity.getStartTime());

        return activityResponse;
    }

    public ResponseEntity<List<ActivityResponse>> getUserActivities(String userId) {
        try{
            List<Activity> activityList = activityRepository.findByUserId(userId);
            if(activityList == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok( activityList.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList())
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
