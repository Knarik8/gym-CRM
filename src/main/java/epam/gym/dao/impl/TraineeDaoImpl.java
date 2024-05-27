package epam.gym.dao.impl;

import epam.gym.dao.TraineeDao;
import epam.gym.entity.Trainee;
import epam.gym.storage.InMemoryStorage;
import epam.gym.util.ProfileGenerationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    private static Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);
    private final InMemoryStorage storage;

    public TraineeDaoImpl(InMemoryStorage storage){
        this.storage = storage;
    }

    @Override
    public Trainee createTrainee(Trainee trainee){

        Set<String> existingUsernames = getExistingUsernames();
        String username = ProfileGenerationHelper.generateUsername(trainee.getFirstName(), trainee.getLastName(),
                existingUsernames);
        String password = ProfileGenerationHelper.generatePassword();

        trainee.setUsername(username);
        trainee.setPassword(password);

        storage.getTraineeStorage().put(trainee.getId(), trainee);
        logger.info("Created trainee with ID: {}", trainee.getId());
        return trainee;
    }

    @Override
    public Trainee updateTraineeById(Long id, Trainee trainee) {
        if (storage.getTraineeStorage().containsKey(id)) {
            storage.getTraineeStorage().put(id, trainee);
            logger.info("Updated trainee with ID: {}", id);
            return trainee;
        }
        logger.warn("Failed to update trainee. Trainee with ID: {} not found.", id);
        return null;
    }

    @Override
    public Trainee selectTraineeById(Long id) {
        Trainee trainee = storage.getTraineeStorage().get(id);
        if (trainee != null) {
            logger.info("Selected trainee with ID: {}", id);
        } else {
            logger.warn("Failed to select trainee. Trainee with ID: {} not found.", id);
        }
        return trainee;
    }

    @Override
    public Trainee deleteTraineeById(Long id) {
        Trainee trainee = storage.getTraineeStorage().remove(id);
        if (trainee != null) {
            logger.info("Deleted trainee with ID: {}", id);
        } else {
            logger.warn("Failed to delete trainee. Trainee with ID: {} not found.", id);
        }
        return trainee;
    }

    @Override
    public Set<String> getExistingUsernames() {
        return storage.getTraineeStorage().values().stream()
                .map(Trainee::getUsername)
                .collect(Collectors.toSet());
    }
}
