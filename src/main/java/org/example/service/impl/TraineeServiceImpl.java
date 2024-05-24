package org.example.service.impl;

import org.example.dao.impl.TraineeDaoImpl;
import org.example.model.entity.Trainee;
import org.example.service.TraineeService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeDaoImpl traineeDaoImpl;

    public TraineeServiceImpl(TraineeDaoImpl traineeDaoImpl){
        this.traineeDaoImpl = traineeDaoImpl;
    }

    @Override
    public Trainee create(Trainee trainee) {
        Set<String> existingUsernames = traineeDaoImpl.getExistingUsernames();

        logger.info("Created trainee with ID: {}", trainee.getId());
        return traineeDaoImpl.create(trainee);
    }

    @Override
    public Trainee update(UUID id, Trainee trainee) {
        Trainee updatedTrainee = traineeDaoImpl.update(id, trainee);
        if (updatedTrainee != null) {
            logger.info("Updated trainee with ID: {}", id);
        } else {
            logger.warn("Failed to update trainee. Trainee with ID: {} not found.", id);
        }
        return updatedTrainee;
    }

    @Override
    public Trainee delete(UUID id) {
        Trainee deletedTrainee = traineeDaoImpl.delete(id);
        if (deletedTrainee != null) {
            logger.info("Deleted trainee with ID: {}", id);
        } else {
            logger.warn("Failed to delete trainee. Trainee with ID: {} not found.", id);
        }
        return deletedTrainee;
    }

    @Override
    public Trainee select(UUID id) {
        Trainee selectedTrainee = traineeDaoImpl.select(id);
        if (selectedTrainee != null) {
            logger.info("Selected trainee with ID: {}", id);
        } else {
            logger.warn("Failed to select trainee. Trainee with ID: {} not found.", id);
        }
        return selectedTrainee;
    }
}
