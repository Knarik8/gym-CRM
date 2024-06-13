package epam.gym.service.hibernateImpl;

import epam.gym.dao.TrainingTypeDao;
import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;
import epam.gym.service.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    private TrainingTypeDao trainingTypeDao;

    public TrainingTypeServiceImpl(TrainingTypeDao trainingTypeDao){
        this.trainingTypeDao = trainingTypeDao;
    }

    @Override
    public TrainingTypeEntity create(TrainingTypeEntity trainingTypeEntity) {
        TrainingTypeEntity createdTrainingTypeEntity = trainingTypeDao.create(trainingTypeEntity);
        logger.info("Training type created with ID: {}", createdTrainingTypeEntity.getId());
        return createdTrainingTypeEntity;
    }

    @Override
    public TrainingTypeEntity findByName(TrainingType trainingType) {
        TrainingTypeEntity trainingTypeEntity = trainingTypeDao.findByName(trainingType);
        if (trainingTypeEntity != null) {
            logger.info("Found training type with name: {}", trainingType);
        } else {
            logger.warn("Training type with name: {} not found", trainingType);
        }
        return trainingTypeEntity;
    }

}
