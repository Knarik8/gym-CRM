package epam.gym.entity;

import java.time.Duration;
import java.util.Set;

public class Training extends BaseEntity{

    private Long trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private Set<DayOfWeek> trainingDays;
    private Duration trainingDuration;

    public Training(Long id, Long trainerId, String trainingName, TrainingType trainingType, Set<DayOfWeek> trainingDays,
                    Duration trainingDuration){
        super(id);
        this.trainerId = trainerId;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDays = trainingDays;
        this.trainingDuration = trainingDuration;

    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Set<DayOfWeek> getTrainingDays() {
        return trainingDays;
    }

    public void setTrainingDays(Set<DayOfWeek> trainingDays) {
        this.trainingDays = trainingDays;
    }

    public Duration getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Duration trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainerId=" + trainerId +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDays=" + trainingDays +
                ", trainingDuration=" + trainingDuration.toMinutes() + "min" +
                '}';
    }
}
