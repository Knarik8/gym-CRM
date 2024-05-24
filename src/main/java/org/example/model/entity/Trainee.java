package org.example.model.entity;

import java.time.LocalDate;
import java.util.UUID;

public class Trainee extends User {

    private LocalDate dateOfBirth;
    private Address address;

    public Trainee(UUID id, String firstName, String lastName, String username, String password, boolean isActive,
                   LocalDate dateOfBirth, Address address) {
        super(id, firstName, lastName, username, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return super.toString() + ", Date of Birth: " + dateOfBirth + ", Address: " + address;
    }


}
