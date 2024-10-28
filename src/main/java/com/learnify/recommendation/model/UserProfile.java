package com.learnify.recommendation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String courseHistory; // List of course IDs user has taken
    private String interests;     // User’s interests (e.g., "marketing", "design")
    private int engagementScore;  // Engagement level (e.g., time spent learning)
    private String learningGoals; // Tracks learning objectives
    private String currentLearningPath; // Stores the user's current learning path (optional)
}
