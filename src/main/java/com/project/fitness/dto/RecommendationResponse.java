package com.project.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private String recommendation;
    private List<String> improvement;
    private List<String> suggestion;
    private List<String> safety;
}
