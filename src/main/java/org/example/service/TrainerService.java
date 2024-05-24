package org.example.service;

import org.example.model.entity.Trainer;

import java.util.UUID;

public interface TrainerService {

    Trainer create(Trainer trainer);
    Trainer update(UUID id, Trainer trainer);
    Trainer select(UUID id);
}
