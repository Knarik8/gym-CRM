package epam.gym.dto.training;

import epam.gym.entity.DayOfWeek;
import lombok.Data;

import java.time.Duration;
import java.util.Set;

@Data
public class TrainingDto {

    private String traineeUsername;
    private String trainerUsername;
    private String trainingName;
    private Long trainingTypeId;
    private Set<DayOfWeek> trainingDays;
    private Duration trainingDuration;
}
