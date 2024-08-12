package epam.gym.service;

import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.Training;

import java.util.Optional;

public interface TrainingService {
    Training create(TrainingDto trainingDto);
    Optional<Training> findById(Long id);
}
