package org.example.service;

import org.example.model.entity.Trainee;

import java.util.UUID;

public interface TraineeService {

    Trainee create(Trainee trainee);
    Trainee update(UUID id, Trainee trainee);
    Trainee delete(UUID id);
    Trainee select(UUID id);
}
