package com.learnify.recommendation.repository;

import com.learnify.recommendation.model.CourseFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseFeedbackRepository extends JpaRepository<CourseFeedback, Long> {
}