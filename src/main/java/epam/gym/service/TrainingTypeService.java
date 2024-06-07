package epam.gym.service;

import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;

public interface TrainingTypeService {

    TrainingTypeEntity create(TrainingTypeEntity trainingTypeEntity);

    TrainingTypeEntity selectByName(TrainingType trainingType);


}
