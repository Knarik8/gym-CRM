package epam.gym.entity;

public class Trainer extends User {

    private TrainingType specialization;


    public Trainer(Long id, String firstName, String lastName, String username, String password, boolean isActive,
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
