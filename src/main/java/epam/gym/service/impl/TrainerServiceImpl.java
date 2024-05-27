package epam.gym.service.impl;

import epam.gym.dao.TrainerDao;
import epam.gym.entity.Trainer;
import epam.gym.service.TrainerService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TrainerDao trainerDaoImpl;

    public TrainerServiceImpl(TrainerDao trainerDaoImpl){
        this.trainerDaoImpl = trainerDaoImpl;
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        logger.info("Created trainer with ID: {}", trainer.getId());
        return trainerDaoImpl.createTrainer(trainer);
    }

    @Override
    public Trainer updateTrainerById(Long id, Trainer trainer) {
        Trainer updatedTrainer = trainerDaoImpl.updateTrainerById(id, trainer);
        if (updatedTrainer != null) {
            logger.info("Updated trainer with ID: {}", id);
        } else {
            logger.warn("Failed to update trainer. Trainer with ID: {} not found.", id);
        }
        return updatedTrainer;
    }

    @Override
    public Trainer selectTrainerById(Long id) {
        Trainer selectedTrainer = trainerDaoImpl.selectTrainerById(id);
        if (selectedTrainer != null) {
            logger.info("Selected trainer with ID: {}", id);
        } else {
            logger.warn("Failed to select trainer. Trainer with ID: {} not found.", id);
        }
        return selectedTrainer;
    }

}
