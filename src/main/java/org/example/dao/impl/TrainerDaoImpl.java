package org.example.dao.impl;

import org.example.dao.TrainerDao;
import org.example.model.entity.Trainer;
import org.example.storage.InMemoryStorage;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private InMemoryStorage storage;

    public TrainerDaoImpl(InMemoryStorage storage){
        this.storage = storage;
    }

    @Override
    public Trainer create(Trainer trainer) {
        storage.getTrainerStorage().put(trainer.getId(), trainer);
        logger.info("Created trainer with ID: {}", trainer.getId());
        return trainer;
    }

    @Override
    public Trainer update(UUID id, Trainer trainer) {
        if (storage.getTrainerStorage().containsKey(id)) {
            storage.getTrainerStorage().put(id, trainer);
            logger.info("Updated trainer with ID: {}", id);
            return trainer;
        }
        logger.warn("Failed to update trainer. Trainer with ID: {} not found.", id);
        return null;
    }

    @Override
    public Trainer select(UUID id) {
        Trainer trainer = storage.getTrainerStorage().get(id);
        if (trainer != null) {
            logger.info("Selected trainer with ID: {}", id);
        } else {
            logger.warn("Failed to select trainer. Trainer with ID: {} not found.", id);
        }
        return trainer;
    }

    @Override
    public Set<String> getExistingUsernames() {
        return storage.getTrainerStorage().values().stream()
                .map(Trainer::getUsername)
                .collect(Collectors.toSet());
    }

}
