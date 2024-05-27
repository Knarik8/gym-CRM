package epam.gym.service;

import epam.gym.entity.Trainee;

public interface TraineeService {

    Trainee createTrainee(Trainee trainee);
    Trainee updateTraineeById(Long id, Trainee trainee);
    Trainee deleteTraineeById(Long id);
    Trainee selectTraineeById(Long id);
}
