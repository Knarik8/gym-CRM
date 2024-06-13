package epam.gym.service;

import epam.gym.entity.Trainer;
import epam.gym.entity.Training;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

public interface TrainerService {

    Trainer create(Trainer trainer);

    Trainer update(Long id, Trainer updatedTrainer, String username, String password) throws AuthenticationException;

    Optional<Trainer> findById(long id, String username, String password) throws AuthenticationException;

    Optional<Trainer> findTrainerByUsername(String username);

    Optional<Trainer> changePassword(Long id, String username, String oldPassword, String newPassword) throws AuthenticationException;

    void activateTrainer(Long id, String username, String password) throws AuthenticationException;

    void deactivateTrainer(Long id, String username, String password) throws AuthenticationException;

    List<Training> getTrainingsByTrainerUsernameAndTraineeName(
            String trainerUsername, String traineeName, Long trainerId, String trainerPassword) throws AuthenticationException;

    List<Trainer> getUnassignedTrainersByTraineeUsername(String traineeUsername);

    }
