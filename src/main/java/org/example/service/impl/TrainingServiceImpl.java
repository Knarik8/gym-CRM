package org.example.service.impl;

import org.example.dao.impl.TrainingDaoImpl;
import org.example.model.entity.Training;
import org.example.service.TrainingService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private TrainingDaoImpl trainingDaoImpl;

    public TrainingServiceImpl(TrainingDaoImpl trainingDaoImpl){
        this.trainingDaoImpl = trainingDaoImpl;
    }

    @Override
    public Training create(Training training) {
        logger.info("Created training with ID: {}", training.getId());
        return trainingDaoImpl.create(training);
    }

    @Override
    public Training select(UUID id) {
        Training selectedTraining = trainingDaoImpl.select(id);
        if (selectedTraining != null) {
            logger.info("Selected training with ID: {}", id);
        } else {
            logger.warn("Failed to select training. Training with ID: {} not found.", id);
        }
        return selectedTraining;
    }
}
