package epam.gym.storage;

import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class InMemoryStorage {

    private Map<Long, Trainee> traineeStorage = new HashMap<>();
    private Map<Long, Trainer> trainerStorage = new HashMap<>();
    private Map<Long, Training> trainingStorage = new HashMap<>();

    public Map<Long, Trainee> getTraineeStorage() {
        return traineeStorage;
    }

    public Map<Long, Trainer> getTrainerStorage() {
        return trainerStorage;
    }

    public Map<Long, Training> getTrainingStorage() {
        return trainingStorage;
    }
}
