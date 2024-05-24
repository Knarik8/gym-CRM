package org.example.dao;

import org.example.model.entity.Training;

import java.util.UUID;

public interface TrainingDao {

    Training create(Training training);
    Training select(UUID id);
}
