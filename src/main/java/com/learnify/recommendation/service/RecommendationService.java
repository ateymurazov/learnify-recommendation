package com.learnify.recommendation.service;

import com.learnify.recommendation.model.UserProfile;
import com.learnify.recommendation.repository.UserRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jpmml.evaluator.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class RecommendationService {

    @Autowired
    private UserRepository userRepository;

    private final ModelEvaluator<?> recommendationModel;

    @Autowired
    public RecommendationService() throws IOException, JAXBException {
        try (FileInputStream fis = new FileInputStream("path/to/your/model.pmml")) {
            PMML pmml = PMMLUtil.unmarshal(fis);
            ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
            this.recommendationModel = modelEvaluatorFactory.newModelEvaluator(pmml);
        }
    }

    public List<String> recommendCourses(String username) {
        // Fetch user data
        UserProfile user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Create a map of input fields for the PMML model
        Map<FieldName, Object> inputFields = new HashMap<>();
        inputFields.put(FieldName.create("courseHistory"), user.getCourseHistory());
        inputFields.put(FieldName.create("interests"), user.getInterests());
        inputFields.put(FieldName.create("engagementScore"), user.getEngagementScore());

        // Set input fields to the model
        Map<FieldName, Object> arguments = new LinkedHashMap<>();
        for (InputField inputField : recommendationModel.getInputFields()) {
            FieldName inputFieldName = inputField.getName();
            Object value = inputFields.get(inputFieldName);
            arguments.put(inputFieldName, value);
        }

        // Evaluate the model
        Map<TargetField, ?> results = recommendationModel.evaluate(arguments);
        List<String> recommendedCourses = parseResults(results);

        return recommendedCourses;
    }

    private List<String> parseResults(Map<TargetField, ?> results) {
        // Parse PMML results to retrieve course recommendations
        // This will depend on your PMML model output
        // Here, I'm providing an example parsing logic
        List<String> courses = new ArrayList<>();
        for (Map.Entry<TargetField, ?> entry : results.entrySet()) {
            // Example: Assuming that the results contain a list of recommended course names as String
            courses.add(entry.getValue().toString());
        }
        return courses;
    }
}