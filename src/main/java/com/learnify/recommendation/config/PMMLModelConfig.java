package com.learnify.recommendation.config;

import org.dmg.pmml.Model;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.PMMLUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Configuration
public class PMMLModelConfig {

    @Bean
    public ModelEvaluator<?> recommendationModel() throws Exception {
        File pmmlFile = new File("src/main/resources/pmml/recommendation_model.pmml");  // Adjust the path to your PMML file

        try (InputStream fis = new FileInputStream(pmmlFile)) {
            // Unmarshal the PMML file to obtain a PMML object using Jakarta JAXB
            PMML pmml = unmarshalPMML(fis);

            // Extract the first model from the PMML (assuming you have only one model in the PMML file)
            List<Model> models = pmml.getModels();
            if (models.isEmpty()) {
                throw new IllegalStateException("No models found in the PMML file.");
            }
            Model model = models.get(0);  // Extract the first model

            // Create a ModelEvaluator using the PMML and Model instance
            ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
            return modelEvaluatorFactory.newModelEvaluator(pmml, model);  // Pass both PMML and Model
        }
    }

    private PMML unmarshalPMML(InputStream inputStream) throws JAXBException {
        // Create a JAXBContext for PMML class
        JAXBContext context = JAXBContext.newInstance(PMML.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (PMML) unmarshaller.unmarshal(new StreamSource(inputStream));
    }
}