package epam.gym.dao;

import epam.gym.entity.Trainer;
import epam.gym.entity.Training;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrainerDao {

    Trainer create(Trainer trainer);

    Optional<Trainer> findById(Long id);

    Trainer update(Trainer trainer);

    Set<String> getExistingUsernames();

    Optional<Trainer> selectTrainerByUsername(String username);

    Optional<Trainer> changePassword(Long id, String newPassword);

    void setActiveStatus(Long id, boolean isActive);

    List<Training> getTrainingsByTrainerUsernameAndTraineeName(String trainerUsername, String traineeName);

    List<Trainer> getUnassignedTrainersByTraineeUsername(String traineeUsername);




}
