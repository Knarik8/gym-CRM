package org.example.dao;

import org.example.model.entity.Trainee;

import java.util.Set;
import java.util.UUID;

public interface TraineeDao {

    Trainee create(Trainee trainee);
    Trainee update(UUID id, Trainee trainee);
    Trainee select(UUID id);
    Trainee delete(UUID id);
    Set<String> getExistingUsernames();
}
