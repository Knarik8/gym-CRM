package epam.gym.storage;

import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
@Getter
@Data
public class InMemoryStorage {

    private Map<Long, Trainee> traineeStorage = new HashMap<>();
    private Map<Long, Trainer> trainerStorage = new HashMap<>();
    private Map<Long, Training> trainingStorage = new HashMap<>();

}
