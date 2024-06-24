package epam.gym.service;

import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;

import java.util.List;

public interface TrainingTypeService {

    TrainingTypeEntity create(TrainingTypeEntity trainingTypeEntity);

    TrainingTypeEntity findByName(TrainingType trainingType);

    List<TrainingTypeEntity> getAllTrainingTypes();



}
