package epam.gym.dao;

import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;

import java.util.List;

public interface TrainingTypeDao {

    TrainingTypeEntity create(TrainingTypeEntity trainingType);

    TrainingTypeEntity findByName(TrainingType trainingType);

    List<TrainingTypeEntity> getAllTrainingTypes();
}
