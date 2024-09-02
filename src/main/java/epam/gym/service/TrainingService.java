package epam.gym.service;

import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.ActionType;
import epam.gym.entity.Training;

import java.util.Optional;

public interface TrainingService {
    Training create(TrainingDto trainingDto);
    Optional<Training> findById(Long id);
    void notifyTrainingUpdate(Training training, ActionType actionType);
    void delete(Long id);
    void convertAndSendToConsumer(Training training, String destination, ActionType actionType);
}
