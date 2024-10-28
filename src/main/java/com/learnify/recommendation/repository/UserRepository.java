package com.learnify.recommendation.repository;

import com.learnify.recommendation.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
}
