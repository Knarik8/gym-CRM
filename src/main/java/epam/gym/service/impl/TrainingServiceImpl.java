package epam.gym.service.impl;

import epam.gym.dao.TrainingDao;
import epam.gym.entity.Training;
import epam.gym.service.TrainingService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private TrainingDao trainingDaoImpl;

    public TrainingServiceImpl(TrainingDao trainingDaoImpl){
        this.trainingDaoImpl = trainingDaoImpl;
    }

    @Override
    public Training createTraining(Training training) {
        logger.info("Created training with ID: {}", training.getId());
        return trainingDaoImpl.createTraining(training);
    }

    @Override
    public Training selectTrainingById(Long id) {
        Training selectedTraining = trainingDaoImpl.selectTrainingById(id);
        if (selectedTraining != null) {
            logger.info("Selected training with ID: {}", id);
        } else {
            logger.warn("Failed to select training. Training with ID: {} not found.", id);
        }
        return selectedTraining;
    }
}
