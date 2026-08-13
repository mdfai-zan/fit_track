package com.project.fitness.service;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.dto.RecommendationResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public ResponseEntity<RecommendationResponse> generate(RecommendationRequest recommendationRequest) {

        try{
            User user = userRepository.findById(recommendationRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("user not found"+ recommendationRequest.getUserId()));

            Activity activity = activityRepository.findById(recommendationRequest.getActivityId())
                    .orElseThrow(() -> new RuntimeException("Recommendation not found"+ recommendationRequest.getActivityId()));

            Recommendation recommendation = Recommendation.builder()
                    .user(user)
                    .activity(activity)
                    .recommendation(recommendationRequest.getRecommendation())
                    .improvement(recommendationRequest.getImprovement())
                    .suggestion(recommendationRequest.getSuggestion())
                    .safety(recommendationRequest.getSafety())
                    .recommendation(recommendationRequest.getRecommendation())
                    .build();
            Recommendation savedRecommendation = recommendationRepository.save(recommendation);
            return ResponseEntity.ok(mappedToResponse(savedRecommendation));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public RecommendationResponse mappedToResponse(Recommendation savedRecommendation){
        RecommendationResponse response = new RecommendationResponse();

        response.setRecommendation(savedRecommendation.getRecommendation());
        response.setSafety(savedRecommendation.getSafety());
        response.setImprovement(savedRecommendation.getImprovement());
        response.setSuggestion(savedRecommendation.getSuggestion());

        return response;
    }

    public ResponseEntity<List<Recommendation>> getActivityByUser(String userId) {
        try{
            List<Recommendation> responseList = recommendationRepository.findByUser_id(userId);
            if(responseList == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Recommendation>> getRecommendationByActivity(String activityId) {

        try{
            List<Recommendation> responseList = recommendationRepository.findByActivity_id(activityId);
            if(responseList == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
