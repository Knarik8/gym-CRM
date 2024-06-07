package epam.gym.service;

import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TraineeService {

    Trainee create(Trainee trainee);

    Trainee update(Long id, Trainee updatedTrainee, String username, String password) throws AuthenticationException;

    Optional<Trainee> findById(long id, String username, String password) throws AuthenticationException;

    void deleteTraineeById(Long id, String username, String password) throws AuthenticationException;

    Optional<Trainee> selectTraineeByUsername(String username);

    Optional<Trainee> changePassword(Long id, String username, String oldPassword, String newPassword) throws AuthenticationException;

    void activateTrainee(Long id, String username, String password) throws AuthenticationException;

    void deactivateTrainee(Long id, String username, String password) throws AuthenticationException;

    boolean deleteTraineeByUsername(String username, String password) throws AuthenticationException;

    public List<Training> getTrainingsByTraineeUsernameAndTrainerName(
            String traineeUsername, String trainerName, Long traineeId, String traineePassword) throws AuthenticationException;

    Trainee updateTraineeTrainersList(Long traineeId, Set<Trainer> trainers);

}
