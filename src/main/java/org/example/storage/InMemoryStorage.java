package org.example.storage;

import org.example.model.entity.Trainer;
import org.example.model.entity.Trainee;
import org.example.model.entity.Training;

import org.springframework.stereotype.Component;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

@Component
public class InMemoryStorage {

    private Map<UUID, Trainee> traineeStorage = new HashMap<>();
    private Map<UUID, Trainer> trainerStorage = new HashMap<>();
    private Map<UUID, Training> trainingStorage = new HashMap<>();

    public Map<UUID, Trainee> getTraineeStorage() {
        return traineeStorage;
    }

    public Map<UUID, Trainer> getTrainerStorage() {
        return trainerStorage;
    }

    public Map<UUID, Training> getTrainingStorage() {
        return trainingStorage;
    }
}
