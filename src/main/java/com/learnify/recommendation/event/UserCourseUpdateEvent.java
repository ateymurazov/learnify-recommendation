package com.learnify.recommendation.event;

public class UserCourseUpdateEvent {
    private String userId;
    private String courseId;

    public UserCourseUpdateEvent(String userId, String courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }
}