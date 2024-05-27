package epam.gym.dao;

import epam.gym.entity.Trainee;

import java.util.Set;

public interface TraineeDao {

    Trainee createTrainee(Trainee trainee);
    Trainee updateTraineeById(Long id, Trainee trainee);
    Trainee selectTraineeById(Long id);
    Trainee deleteTraineeById(Long id);
    Set<String> getExistingUsernames();
}
