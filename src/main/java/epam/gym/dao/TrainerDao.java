package epam.gym.dao;

import epam.gym.entity.Trainer;

import java.util.Set;

public interface TrainerDao {

    Trainer createTrainer(Trainer trainer);
    Trainer updateTrainerById(Long id, Trainer trainer);
    Trainer selectTrainerById(Long id);
    Set<String> getExistingUsernames();

}
