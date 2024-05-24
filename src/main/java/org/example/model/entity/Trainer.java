package org.example.model.entity;

import org.example.model.enums.TrainingType;

import java.util.UUID;

public class Trainer extends User {

    private TrainingType specialization;


    public Trainer(UUID id, String firstName, String lastName, String username, String password, boolean isActive,
                   TrainingType specialization){
        super(id, firstName, lastName, username, password, isActive);
        this.specialization = specialization;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return super.toString() + ", Specialization: " + specialization;
    }
}
