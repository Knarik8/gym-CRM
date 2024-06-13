package epam.gym.dao;

import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;

public interface TrainingTypeDao {

    TrainingTypeEntity create(TrainingTypeEntity trainingType);

    TrainingTypeEntity findByName(TrainingType trainingType);
}
