package epam.gym.dao;

import epam.gym.entity.Training;

import java.util.Optional;

public interface TrainingDao {

    Training create(Training training);

    Optional<Training> findById(Long id);

}
