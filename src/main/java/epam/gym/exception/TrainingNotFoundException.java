package epam.gym.exception;


public class TrainingNotFoundException extends RuntimeException {

    public TrainingNotFoundException(Long id) {
        super(String.format("Training with id: %s was not found", id));
    }

}
