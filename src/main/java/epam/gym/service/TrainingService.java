package epam.gym.service;

import epam.gym.entity.Training;

import java.util.Optional;

public interface TrainingService {
    Training create(Training training);
    Optional<Training> findById(Long id);
}
