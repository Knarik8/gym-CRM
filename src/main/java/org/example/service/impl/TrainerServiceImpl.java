package org.example.service.impl;

import org.example.dao.impl.TrainerDaoImpl;
import org.example.model.entity.Trainer;
import org.example.service.TrainerService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TrainerDaoImpl trainerDaoImpl;

    public TrainerServiceImpl(TrainerDaoImpl trainerDaoImpl){
        this.trainerDaoImpl = trainerDaoImpl;
    }

    @Override
    public Trainer create(Trainer trainer) {
        logger.info("Created trainer with ID: {}", trainer.getId());
        return trainerDaoImpl.create(trainer);
    }

    @Override
    public Trainer update(UUID id, Trainer trainer) {
        Trainer updatedTrainer = trainerDaoImpl.update(id, trainer);
        if (updatedTrainer != null) {
            logger.info("Updated trainer with ID: {}", id);
        } else {
            logger.warn("Failed to update trainer. Trainer with ID: {} not found.", id);
        }
        return updatedTrainer;
    }

    @Override
    public Trainer select(UUID id) {
        Trainer selectedTrainer = trainerDaoImpl.select(id);
        if (selectedTrainer != null) {
            logger.info("Selected trainer with ID: {}", id);
        } else {
            logger.warn("Failed to select trainer. Trainer with ID: {} not found.", id);
        }
        return selectedTrainer;
    }

}
