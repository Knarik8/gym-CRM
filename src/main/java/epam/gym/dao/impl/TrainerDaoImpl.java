package epam.gym.dao.impl;

import epam.gym.entity.Trainer;
import epam.gym.storage.InMemoryStorage;
import epam.gym.dao.TrainerDao;
import epam.gym.util.ProfileGenerationHelper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private InMemoryStorage storage;

    public TrainerDaoImpl(InMemoryStorage storage){
        this.storage = storage;
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        Set<String> existingUsernames = getExistingUsernames();
        String username = ProfileGenerationHelper.generateUsername(trainer.getFirstName(), trainer.getLastName(),
                existingUsernames);
        String password = ProfileGenerationHelper.generatePassword();

        trainer.setUsername(username);
        trainer.setPassword(password);

        storage.getTrainerStorage().put(trainer.getId(), trainer);
        logger.info("Created trainer with ID: {}", trainer.getId());
        return trainer;
    }

    @Override
    public Trainer updateTrainerById(Long id, Trainer trainer) {
        if (storage.getTrainerStorage().containsKey(id)) {
            storage.getTrainerStorage().put(id, trainer);
            logger.info("Updated trainer with ID: {}", id);
            return trainer;
        }
        logger.warn("Failed to update trainer. Trainer with ID: {} not found.", id);
        return null;
    }

    @Override
    public Trainer selectTrainerById(Long id) {
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
