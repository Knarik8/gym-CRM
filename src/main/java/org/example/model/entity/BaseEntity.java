package org.example.model.entity;

import java.util.UUID;

public class BaseEntity {

    private UUID id;

    BaseEntity(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
