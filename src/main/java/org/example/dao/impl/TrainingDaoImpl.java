package org.example.dao.impl;

import org.example.dao.TrainingDao;
import org.example.model.entity.Training;
import org.example.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TrainingDaoImpl implements TrainingDao {

    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);
    private InMemoryStorage storage;

    public TrainingDaoImpl(InMemoryStorage storage){
        this.storage = storage;

    }

    @Override
    public Training create(Training training) {
        storage.getTrainingStorage().put(training.getId(), training);
        logger.info("Created training with ID: {}", training.getId());
        return training;
    }

    @Override
    public Training select(UUID id) {
        Training training = storage.getTrainingStorage().get(id);
        if (training != null) {
            logger.info("Selected training with ID: {}", id);
        } else {
            logger.warn("Failed to select training. Training with ID: {} not found.", id);
        }
        return training;
    }
}
