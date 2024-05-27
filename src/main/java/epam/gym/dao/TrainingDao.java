package epam.gym.dao;

import epam.gym.entity.Training;

public interface TrainingDao {

    Training createTraining(Training training);
    Training selectTrainingById(Long id);
}
