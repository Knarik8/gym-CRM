package epam.gym.storage;

import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;
import lombok.Data;

import java.util.List;

@Data
public class DataWrapper {

    private List<Trainee> trainees;
    private List<Trainer> trainers;
    private List<Training> trainings;

}
