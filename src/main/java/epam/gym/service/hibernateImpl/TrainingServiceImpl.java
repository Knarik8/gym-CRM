package epam.gym.service.hibernateImpl;

import epam.gym.dao.TrainingDao;
import epam.gym.entity.Training;
import epam.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private TrainingDao trainingDao;

    public TrainingServiceImpl(TrainingDao trainingDao){
        this.trainingDao = trainingDao;
    }
    @Override
    public Training create(Training training) {
        Training createdTraining = trainingDao.create(training);
        logger.info("Training created with ID: {}", createdTraining.getId());
        return createdTraining;    }

    @Override
    public Optional<Training> findById(Long id) {
        Optional<Training> training = trainingDao.findById(id);
        if (training != null) {
            logger.info("Found training with ID: {}", id);
            return training;
        } else {
            logger.warn("Training with ID: {} not found", id);
        }
        return Optional.empty();
    }

}
