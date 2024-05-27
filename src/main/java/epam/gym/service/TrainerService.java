package epam.gym.service;

import epam.gym.entity.Trainer;

public interface TrainerService {

    Trainer createTrainer(Trainer trainer);
    Trainer updateTrainerById(Long id, Trainer trainer);
    Trainer selectTrainerById(Long id);
}
