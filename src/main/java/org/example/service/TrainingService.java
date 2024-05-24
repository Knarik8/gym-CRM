package org.example.service;

import org.example.model.entity.Training;

import java.util.UUID;

public interface TrainingService {
    Training create(Training training);
    Training select(UUID id);
}
