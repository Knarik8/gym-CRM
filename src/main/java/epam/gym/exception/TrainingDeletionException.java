package epam.gym.exception;

public class TrainingDeletionException extends RuntimeException{

    public TrainingDeletionException() {
        super("Training could not be deleted.");
    }
}
