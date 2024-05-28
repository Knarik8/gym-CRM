package epam.gym.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Training {

    private Long id;
    private Long trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private Set<DayOfWeek> trainingDays;
    private Duration trainingDuration;

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
