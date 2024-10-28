---

Learnify Recommendation System

Overview

Learnify is an AI-powered personalized recommendation system designed for an e-learning platform. It enhances the user learning experience by providing personalized course recommendations based on user behavior, learning preferences, and engagement history. The system dynamically adapts to user interactions, offering learning paths that evolve as users progress, while utilizing collaborative filtering to surface popular courses taken by similar learners. Furthermore, the system incorporates user feedback (course ratings and completion status) to refine recommendations.

Key Features

1. Personalized Learning Paths
   The system tracks individual learning progress and recommends courses that help users achieve their educational goals.
   Based on user profiles, including course history, interests, and engagement scores, the system provides a personalized learning journey.
2. Dynamic Suggestions
   As users engage with new courses and complete them, the system dynamically updates recommendations in real-time.
   Leveraging Spring Events, it automatically refreshes the recommendations as users’ interactions evolve.
3. Collaborative Filtering
   Learners with similar profiles (e.g., similar learning goals, interests, or professional backgrounds) get recommendations based on the courses that have benefited other users in the same group.
   This collaborative filtering approach helps users discover popular courses they may have missed.
4. Diverse Content Exposure
   The system not only recommends courses based on the user's primary interests but also suggests cross-functional courses that promote well-rounded learning.
   For example, users focused on marketing may receive suggestions for creative or design-related courses to broaden their expertise.
5. Performance Feedback
   The system tracks course ratings and completion status provided by users.
   Courses with higher ratings are promoted in recommendations, ensuring users are directed to high-quality content.
   User feedback is essential in adjusting future recommendations and maintaining a quality learning experience.

---

# **Learnify Recommendation System**

## Overview

**Learnify** is an AI-powered personalized recommendation system designed for an e-learning platform. It enhances the user learning experience by providing personalized course recommendations based on user behavior, learning preferences, and engagement history. The system dynamically adapts to user interactions, offering learning paths that evolve as users progress, while utilizing collaborative filtering to surface popular courses taken by similar learners. Furthermore, the system incorporates user feedback (course ratings and completion status) to refine recommendations.

## Key Features

### 1. **Personalized Learning Paths**
- The system tracks individual learning progress and recommends courses that help users achieve their educational goals.
- Based on user profiles, including course history, interests, and engagement scores, the system provides a personalized learning journey.

### 2. **Dynamic Suggestions**
- As users engage with new courses and complete them, the system dynamically updates recommendations in real-time.
- Leveraging Spring Events, it automatically refreshes the recommendations as users’ interactions evolve.

### 3. **Collaborative Filtering**
- Learners with similar profiles (e.g., similar learning goals, interests, or professional backgrounds) get recommendations based on the courses that have benefited other users in the same group.
- This collaborative filtering approach helps users discover popular courses they may have missed.

### 4. **Diverse Content Exposure**
- The system not only recommends courses based on the user's primary interests but also suggests cross-functional courses that promote well-rounded learning.
- For example, users focused on marketing may receive suggestions for creative or design-related courses to broaden their expertise.

### 5. **Performance Feedback**
- The system tracks course ratings and completion status provided by users.
- Courses with higher ratings are promoted in recommendations, ensuring users are directed to high-quality content.
- User feedback is essential in adjusting future recommendations and maintaining a quality learning experience.

---

## Application Structure

```bash
learnify-recommendation/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── learnify/
│   │   │           └── recommendation/
│   │   │               ├── config/
│   │   │               │   └── PMMLModelConfig.java           # PMML model configuration
│   │   │               ├── controller/
│   │   │               │   └── RecommendationController.java  # REST API for recommendations
│   │   │               ├── model/
│   │   │               │   ├── CourseFeedback.java            # Entity for course feedback (ratings/completions)
│   │   │               │   └── UserProfile.java               # Entity for user profile data (learning progress, etc.)
│   │   │               ├── repository/
│   │   │               │   ├── CourseFeedbackRepository.java  # Repository for storing feedback
│   │   │               │   └── UserRepository.java            # Repository for user profiles
│   │   │               ├── service/
│   │   │               │   ├── CourseFeedbackService.java     # Service for handling feedback
│   │   │               │   └── RecommendationService.java     # Core recommendation logic
│   │   │               ├── event/
│   │   │               │   └── UserCourseUpdateEvent.java     # Event class for user course updates
│   │   │               ├── LearnifyRecommendationApplication.java  # Main Spring Boot application class
│   │   └── resources/
│   │       ├── application.properties                         # Database and app configuration
│   │       └── pmml/recommendation_model.pmml                 # Machine learning model in PMML format
│
├── build.gradle                                                # Gradle dependencies
└── README.md                                                   # Project description and instructions
```

---

## Detailed Functionality

### **1. Model and Profile Entities**
- **`UserProfile.java`**: Stores the user’s profile information, including:
    - `courseHistory`: List of courses the user has completed or enrolled in.
    - `interests`: Areas of interest (e.g., marketing, design) to drive recommendations.
    - `engagementScore`: Tracks how much time a user has spent on the platform.
    - `learningGoals`: Specific goals the user is working toward, which are used to recommend relevant courses.

- **`CourseFeedback.java`**: Stores feedback for courses, including:
    - `rating`: User’s rating for the course.
    - `completed`: Whether the user completed the course.

### **2. Dynamic Recommendations**
The **Recommendation Service** (`RecommendationService.java`) handles the core logic:
- Fetches user profiles based on their username.
- Recommends courses based on user engagement, goals, and interests.
- Incorporates **Collaborative Filtering** by finding users with similar profiles and suggesting courses they've taken.
- Triggers dynamic updates using the `UserCourseUpdateEvent.java` whenever users complete or enroll in new courses.

### **3. Collaborative Filtering**
The service fetches similar users and aggregates courses that have been completed or rated highly by others with matching profiles. This is implemented in the `RecommendationService.java` file.

### **4. Diverse Content Exposure**
The system encourages users to explore courses in fields outside their immediate focus. For example, if a user’s profile indicates interest in marketing, the system might recommend courses in **design** or **SEO**. This promotes a well-rounded learning experience.

### **5. Feedback Integration**
The **Course Feedback Service** (`CourseFeedbackService.java`) stores user feedback and computes average ratings for courses. This feedback is incorporated into the recommendation engine to highlight popular, highly-rated courses.

---

## PMML Integration

The machine learning model for generating recommendations is stored as a **PMML file** (`recommendation_model.pmml`), which is loaded using the **JPMML Evaluator** in the Java-based Spring Boot application.

- **`PMMLModelConfig.java`**: Configures and loads the PMML model using JPMML's `ModelEvaluatorFactory`.
- The trained model is exported in PMML format using a Python script, which uses Scikit-learn and `sklearn2pmml` to generate the `recommendation_model.pmml` file.

Python script example:
```python
from sklearn.ensemble import RandomForestClassifier
from sklearn2pmml import PMMLPipeline, sklearn2pmml
import pandas as pd

# Example data
data = {'courseHistory': [...], 'interests': [...], 'engagementScore': [...], 'recommendation': [...]}
df = pd.DataFrame(data)

# Train model
X = df[['courseHistory', 'interests', 'engagementScore']]
y = df['recommendation']
model = RandomForestClassifier()
model.fit(X, y)

# Export model as PMML
pipeline = PMMLPipeline([("classifier", model)])
sklearn2pmml(pipeline, "src/main/resources/pmml/recommendation_model.pmml", with_repr=True)
```

The model is trained in Python and exported as a **PMML file**, which is then used within the Spring Boot system to generate personalized recommendations.

---

## Running the Application

### 1. Build the project
Run the following command to build the project:
```bash
./gradlew build
```

### 2. Run the application
To start the application, use:
```bash
./gradlew bootRun
```

### 3. Accessing the Recommendations API
Once the application is running, you can access the recommendations API through the following endpoint:

```
GET http://localhost:8080/api/recommendation/{username}
```

This will return a list of recommended courses for the specified user.

---

## Contribution Guidelines

Feel free to contribute to the project by creating a pull request or raising issues for any bugs you encounter.

---

## License

This project is licensed under the MIT License.

---

## Acknowledgments

- **JPMML** for enabling model deployment in Java.
- **scikit-learn** and **sklearn2pmml** for model training and PMML export.

---
