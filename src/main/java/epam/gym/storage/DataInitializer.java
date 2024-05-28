package epam.gym.storage;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final InMemoryStorage storage;

    @Value("${data.file.path}")
    private String dataFilePath;

    public DataInitializer(InMemoryStorage storage) {
        this.storage = storage;
    }

    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Register JavaTimeModule(to understand LocalTime)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  //to ignore unknown properties(isActive in my case)
        try {
            DataWrapper data = objectMapper.readValue(new File(dataFilePath), DataWrapper.class);
            for (Trainee trainee : data.getTrainees()) {
                storage.getTraineeStorage().put(trainee.getId(), trainee);
                logger.info("Loaded trainee with ID: {}", trainee.getId());
            }

            for (Trainer trainer : data.getTrainers()) {
                storage.getTrainerStorage().put(trainer.getId(), trainer);
                logger.info("Loaded trainer with ID: {}", trainer.getId());
            }

            for (Training training : data.getTrainings()) {
                storage.getTrainingStorage().put(training.getId(), training);
                logger.info("Loaded training with ID: {}", training.getId());
            }

        } catch (IOException e) {
            logger.error("Failed to initialize data from file: {}", dataFilePath, e);
        }
    }
}
