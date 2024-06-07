package epam.gym.service.hibernateImpl;

import epam.gym.dao.TraineeDao;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;
import epam.gym.service.TraineeService;
import epam.gym.util.AuthenticationService;
import epam.gym.util.ProfileGenerationHelper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final TraineeDao traineeDao;
    private final AuthenticationService authenticationService;

    public TraineeServiceImpl(TraineeDao traineeDao, AuthenticationService authenticationService) {
        this.traineeDao = traineeDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public Trainee create(Trainee trainee) {
        logger.info("Creating a new trainee with first name: {} and last name: {}", trainee.getFirstName(), trainee.getLastName());
        Set<String> existingUsernames = traineeDao.getExistingUsernames();
        String username = ProfileGenerationHelper.generateUsername(trainee.getFirstName(), trainee.getLastName(),
                existingUsernames);
        logger.info("Generated username: {}", username);
        String password = ProfileGenerationHelper.generatePassword();
        trainee.setUsername(username);
        trainee.setPassword(password);
        Trainee createdTrainee = traineeDao.create(trainee);
        logger.info("Created trainee with ID: {}", createdTrainee.getId());
        return createdTrainee;
    }

    @Override
    public Trainee update(Long id, Trainee updatedTrainee, String username, String password) throws AuthenticationException {
        logger.info("Attempting to update trainee with ID: {}, by user: {}", id, username);
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainee> existingTraineeOpt = traineeDao.findById(id);
            if (existingTraineeOpt.isPresent()) {
                logger.info("Found existing trainee with ID: {}", id);
                Trainee existingTrainee = existingTraineeOpt.get();
                existingTrainee.setFirstName(updatedTrainee.getFirstName());
                existingTrainee.setLastName(updatedTrainee.getLastName());
                existingTrainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
                existingTrainee.setAddress(updatedTrainee.getAddress());
                existingTrainee.setTrainers(updatedTrainee.getTrainers());
                existingTrainee.setTrainings(updatedTrainee.getTrainings());
                logger.info("Updating trainee with new details: {}", updatedTrainee);
                return traineeDao.update(existingTrainee);
            } else {
                logger.warn("Trainee with ID: {} not found", id);
                throw new IllegalArgumentException("Trainee not found");
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public Optional<Trainee> findById(long id, String username, String password) throws AuthenticationException {
        logger.info("Attempting to find trainee with ID: {}, by user: {}", id, username);
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainee> existingTraineeOpt = traineeDao.findById(id);
            if (existingTraineeOpt.isPresent()) {
                logger.info("Found trainee with ID: {}", id);
                return existingTraineeOpt;
            } else {
                logger.warn("Failed to select trainee. Trainee with ID: {} not found.", id);
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
        return Optional.empty();
    }

    @Override
    public void deleteTraineeById(Long id, String username, String password) throws AuthenticationException {
        logger.info("Attempting to delete trainee with ID: {}, by user: {}", id, username);
        if (authenticationService.authenticate(id, username, password)) {
            traineeDao.deleteById(id);
            logger.info("Deleted trainee with ID: {}", id);
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public Optional<Trainee> selectTraineeByUsername(String username) {
        Optional<Trainee> traineeOpt = traineeDao.selectTraineeByUsername(username);
        if (traineeOpt.isPresent()) {
            logger.info("Found trainee with username: {}", username);
        } else {
            logger.warn("Failed to find trainee with username: {}", username);
        }
        return traineeOpt;
    }

    @Override
    public Optional<Trainee> changePassword(Long id, String username, String oldPassword, String newPassword) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, oldPassword)) {
            Optional<Trainee> updatedTrainee = traineeDao.changePassword(id, newPassword);
            if (updatedTrainee.isPresent()) {
                logger.info("Password changed successfully for trainee with ID: {}", id);
            } else {
                logger.warn("Failed to change password. Trainee with ID: {} not found.", id);
            }
            return updatedTrainee;
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }


    @Override
    public void activateTrainee(Long id, String username, String password) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainee> traineeOpt = findById(id, username, password);
            if (traineeOpt.isPresent()) {
                traineeDao.setActiveStatus(id, true);
                logger.info("Activated trainee with ID: {}", id);
            } else {
                logger.error("Trainee not found with ID: {}", id);
                throw new IllegalArgumentException("Trainee not found");
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public void deactivateTrainee(Long id, String username, String password) throws AuthenticationException {
        if (authenticationService.authenticate(id, username, password)) {
            Optional<Trainee> traineeOpt = findById(id, username, password);
            if (traineeOpt.isPresent()) {
                traineeDao.setActiveStatus(id, false);
                logger.info("Deactivated trainee with ID: {}", id);
            } else {
                logger.error("Trainee not found with ID: {}", id);
                throw new IllegalArgumentException("Trainee not found");
            }
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }

    @Override
    public boolean deleteTraineeByUsername(String username, String password) throws AuthenticationException {
        Optional<Trainee> traineeOptional = traineeDao.selectTraineeByUsername(username);
        if (traineeOptional.isPresent()) {
            Trainee trainee = traineeOptional.get();
            if (authenticationService.authenticate(trainee.getId(), username, password)) {
                boolean result = traineeDao.deleteByUsername(username);
                logger.info("Deleted trainee with username: {}", username);
                return result;
            } else {
                logger.error("Authentication failed for user: {}", username);
                throw new AuthenticationException("Authentication failed for user: " + username);
            }
        } else {
            logger.error("Trainee not found with username: {}", username);
            throw new IllegalArgumentException("Trainee not found");
        }
    }

    @Override
    public List<Training> getTrainingsByTraineeUsernameAndTrainerName(String traineeUsername, String trainerName, Long traineeId, String traineePassword) throws AuthenticationException {
        if (authenticationService.authenticate(traineeId, traineeUsername, traineePassword)) {
            List<Training> trainings = traineeDao.getTrainingsByTraineeUsernameAndTrainerName(traineeUsername, trainerName);
            logger.info("Retrieved trainings for traineeUsername: {} and trainerName: {}", traineeUsername, trainerName);
            return trainings;
        } else {
            logger.error("Authentication failed for user: {}", traineeUsername);
            throw new AuthenticationException("Authentication failed for user: " + traineeUsername);
        }
    }

    @Override
    public Trainee updateTraineeTrainersList(Long traineeId, Set<Trainer> trainers) {
        Trainee updatedTrainee = traineeDao.updateTraineeTrainersList(traineeId, trainers);
        logger.info("Updated trainers list for trainee with ID: {}", traineeId);
        return updatedTrainee;    }


}
