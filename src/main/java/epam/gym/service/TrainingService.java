package epam.gym.service;

import epam.gym.entity.Training;

public interface TrainingService {
    Training createTraining(Training training);
    Training selectTrainingById(Long id);
}
