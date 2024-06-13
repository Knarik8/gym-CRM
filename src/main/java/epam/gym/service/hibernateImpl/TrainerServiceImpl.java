package epam.gym.service.hibernateImpl;

import epam.gym.dao.TrainerDao;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;
import epam.gym.service.TrainerService;
import epam.gym.util.AuthenticationService;
import epam.gym.util.ProfileGenerationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private TrainerDao trainerDao;
    private AuthenticationService authenticationService;

    public TrainerServiceImpl(TrainerDao trainerDaoImpl, AuthenticationService authenticationService){
        this.trainerDao = trainerDaoImpl;
        this.authenticationService = authenticationService;
    }

    @Override
    public Trainer create(Trainer trainer) {
        Set<String> existingUsernames = trainerDao.getExistingUsernames();
        String username = ProfileGenerationHelper.generateUsername(trainer.getFirstName(), trainer.getLastName(),
                existingUsernames);
        String password = ProfileGenerationHelper.generatePassword();
        trainer.setUsername(username);
        trainer.setPassword(password);
        Trainer createdTrainer = trainerDao.create(trainer);
        logger.info("Trainer created with username: {}", username);
        return createdTrainer;
    }

    @Override
    public Trainer update(Long id, Trainer updatedTrainer, String username, String password) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainer> existingTrainerOpt = trainerDao.findById(id);
            if (existingTrainerOpt.isPresent()) {
                Trainer existingTrainer = existingTrainerOpt.get();
                existingTrainer.setFirstName(updatedTrainer.getFirstName());
                existingTrainer.setLastName(updatedTrainer.getLastName());
                existingTrainer.setSpecialization(updatedTrainer.getSpecialization());
                existingTrainer.setTrainees(updatedTrainer.getTrainees());
                existingTrainer.setTrainings(updatedTrainer.getTrainings());
                existingTrainer.setActive(true);
                Trainer updated = trainerDao.update(existingTrainer);
                logger.info("Updated trainer with ID: {}", id);
                return updated;
            } else {
                logger.error("Trainer not found with ID: {}", id);
                throw new IllegalArgumentException("Trainer not found");
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public Optional<Trainer> findById(long id, String username, String password) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainer> existingTrainerOpt = trainerDao.findById(id);
            if (existingTrainerOpt.isPresent()) {
                logger.info("Found trainer with ID: {}", id);
                return existingTrainerOpt;
            } else {
                logger.warn("Trainer with ID: {} not found.", id);
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
        return Optional.empty();
    }
    @Override
    public Optional<Trainer> findTrainerByUsername(String username) {
        Optional<Trainer> trainerOpt = trainerDao.findTrainerByUsername(username);
        if (trainerOpt.isPresent()) {
            logger.info("Found trainer with username: {}", username);
        } else {
            logger.warn("Trainer with username: {} not found", username);
        }

        return trainerOpt;
    }

    @Override
    public Optional<Trainer> changePassword(Long id, String username, String oldPassword, String newPassword) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, oldPassword)) {
            Optional<Trainer> updatedTrainer = trainerDao.changePassword(id, newPassword);
            if (updatedTrainer.isPresent()) {
                logger.info("Password changed successfully for trainer with ID: {}", id);
            } else {
                logger.warn("Failed to change password. Trainer with ID: {} not found", id);
            }

            return updatedTrainer;
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public void activateTrainer(Long id, String username, String password) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainer> trainerOpt = findById(id, username, password);
            if (trainerOpt.isPresent()) {
                trainerDao.setActiveStatus(id, true);
                logger.info("Activated trainer with ID: {}", id);
            } else {
                logger.error("Trainer not found with ID: {}", id);
                throw new IllegalArgumentException("Trainer not found");
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public void deactivateTrainer(Long id, String username, String password) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainer> trainerOpt = findById(id, username, password);
            if (trainerOpt.isPresent()) {
                trainerDao.setActiveStatus(id, false);
                logger.info("Deactivated trainer with ID: {}", id);
            } else {
                logger.error("Trainer not found with ID: {}", id);
                throw new IllegalArgumentException("Trainer not found");
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public List<Training> getTrainingsByTrainerUsernameAndTraineeName(String trainerUsername, String traineeName, Long trainerId, String trainerPassword) throws AuthenticationException {
        if (authenticationService.authenticate(trainerId, trainerUsername, trainerPassword)) {
            List<Training> trainings = trainerDao.getTrainingsByTrainerUsernameAndTraineeName(trainerUsername, traineeName);
            logger.info("Retrieved trainings for trainerUsername: {} and traineeName: {}", trainerUsername, traineeName);
            return trainings;
        } else {
            logger.error("Authentication failed for trainer: {}", trainerUsername);
            throw new AuthenticationException("Authentication failed for trainer: " + trainerUsername);
        }
    }

    @Override
    public List<Trainer> getUnassignedTrainersByTraineeUsername(String traineeUsername) {
        logger.info("Getting unassigned trainers by traineeUsername: {}", traineeUsername);
        List<Trainer> unassignedTrainers = trainerDao.getUnassignedTrainersByTraineeUsername(traineeUsername);
        logger.info("Retrieved unassigned trainers for traineeUsername: {}", traineeUsername);
        return unassignedTrainers;    }
}
