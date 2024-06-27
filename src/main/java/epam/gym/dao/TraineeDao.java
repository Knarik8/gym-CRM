package epam.gym.dao;

import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TraineeDao {

    Trainee create(Trainee trainee);

    Trainee update(Trainee trainee);

    Optional<Trainee> findById(Long id);

    void deleteById(Long id);

    Set<String> getExistingUsernames();

    Optional<Trainee> findTraineeByUsername(String username);

    void setActiveStatus(Long id, boolean isActive);

    boolean deleteByUsername(String username);

    List<Training> getTrainingsByTraineeUsernameAndTrainerName(String traineeUsername, String trainerName);

    Trainee updateTraineeTrainersList(Long traineeId, Set<Trainer> trainers);

    List<Trainer> getNotAssignedTrainers(String traineeUsername);


}
