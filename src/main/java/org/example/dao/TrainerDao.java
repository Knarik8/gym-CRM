package org.example.dao;

import org.example.model.entity.Trainer;

import java.util.Set;
import java.util.UUID;

public interface TrainerDao {

    Trainer create(Trainer trainer);
    Trainer update(UUID id, Trainer trainer);
    Trainer select(UUID id);
    Set<String> getExistingUsernames();

}
