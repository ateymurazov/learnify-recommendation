package com.learnify.recommendation.controller;

import com.learnify.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{username}")
    public List<String> getRecommendations(@PathVariable String username) {
        return recommendationService.recommendCourses(username);
    }
}
