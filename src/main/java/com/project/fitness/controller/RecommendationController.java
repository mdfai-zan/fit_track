package com.project.fitness.controller;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.dto.RecommendationResponse;
import com.project.fitness.model.Recommendation;
import com.project.fitness.service.RecommendationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponse> createRecommendation(@RequestBody RecommendationRequest recommendationRequest){
        return recommendationService.generate(recommendationRequest);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getRecommendationByUser(@PathVariable String userId){
        return recommendationService.getActivityByUser(userId);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recommendation>> getRecommendationByActivity(@PathVariable String activityId){
        return recommendationService.getRecommendationByActivity(activityId);
    }
}
