package epam.gym.service.impl;

import epam.gym.dao.TraineeDao;
import epam.gym.entity.Trainee;
import epam.gym.service.TraineeService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeDao traineeDaoImpl;

    public TraineeServiceImpl(TraineeDao traineeDaoImpl){
        this.traineeDaoImpl = traineeDaoImpl;
    }

    @Override
    public Trainee createTrainee(Trainee trainee) {
        logger.info("Created trainee with ID: {}", trainee.getId());
        return traineeDaoImpl.createTrainee(trainee);
    }

    @Override
    public Trainee updateTraineeById(Long id, Trainee trainee) {
        Trainee updatedTrainee = traineeDaoImpl.updateTraineeById(id, trainee);
        if (updatedTrainee != null) {
            logger.info("Updated trainee with ID: {}", id);
        } else {
            logger.warn("Failed to update trainee. Trainee with ID: {} not found.", id);
        }
        return updatedTrainee;
    }

    @Override
    public Trainee deleteTraineeById(Long id) {
        Trainee deletedTrainee = traineeDaoImpl.deleteTraineeById(id);
        if (deletedTrainee != null) {
            logger.info("Deleted trainee with ID: {}", id);
        } else {
            logger.warn("Failed to delete trainee. Trainee with ID: {} not found.", id);
        }
        return deletedTrainee;
    }

    @Override
    public Trainee selectTraineeById(Long id) {
        Trainee selectedTrainee = traineeDaoImpl.selectTraineeById(id);
        if (selectedTrainee != null) {
            logger.info("Selected trainee with ID: {}", id);
        } else {
            logger.warn("Failed to select trainee. Trainee with ID: {} not found.", id);
        }
        return selectedTrainee;
    }
}
