package epam.gym.service;

import epam.gym.dto.trainer.TrainerDto;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TraineeService {

    Trainee create(Trainee trainee);

    Trainee update(Long id, Trainee updatedTrainee, String username, String password);

    Optional<Trainee> findById(long id, String username, String password);

    void deleteTraineeById(Long id, String username, String password);

    Optional<Trainee> findByUsername(String username);

    void activateTrainee(Long id, String username, String password);

    void deactivateTrainee(Long id, String username, String password);

    boolean deleteTraineeByUsername(String username, String password);

    public List<Training> getTrainingsByTraineeUsernameAndTrainerName(
            String traineeUsername, String trainerName, Long traineeId, String traineePassword);

    Trainee updateTraineeTrainersList(Long traineeId, Set<Trainer> trainers);

    void setActiveStatus(String username, boolean isActive);

    List<Trainer> getNotAssignedTrainers(String traineeUsername);

    List<TrainerDto> updateTraineeTrainers(String traineeUsername, List<String> trainerUsernames);

}
