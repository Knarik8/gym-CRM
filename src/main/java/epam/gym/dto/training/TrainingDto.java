package epam.gym.dto.training;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TrainingDto {

    private String trainingName;
    private Long trainingTypeId;
    private Set<String> trainingDays;
    private Long trainingDuration;
    private Long traineeId;
    private Long trainerId;
    private LocalDateTime trainingDate;}
