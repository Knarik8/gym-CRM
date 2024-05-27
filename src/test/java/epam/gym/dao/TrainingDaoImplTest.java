package epam.gym.dao;

import epam.gym.dao.impl.TrainingDaoImpl;
import epam.gym.entity.Training;
import epam.gym.storage.InMemoryStorage;
import epam.gym.entity.DayOfWeek;
import epam.gym.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingDaoImplTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TrainingDaoImpl trainingDaoImpl;

    private Map<Long, Training> trainingStorage;
    private Training training;
    private Long trainingId;
    private Set<DayOfWeek> trainingDays;
    private Random rd;

    @BeforeEach
    public void setUp(){
        trainingStorage = new HashMap<>();
        rd = new Random();
        trainingId = rd.nextLong();
        trainingDays = new HashSet<>();
        Collections.addAll(trainingDays,DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);

        training = new Training(trainingId, rd.nextLong(), "Cardio training", TrainingType.CARDIO,
                trainingDays, Duration.ofMinutes(50));
        when(storage.getTrainingStorage()).thenReturn(trainingStorage);
    }

    @Test
    public void whenCreateTraining_thenSuccess () {

        trainingDaoImpl.createTraining(training);

        verify(storage).getTrainingStorage();
        assertEquals(trainingId, training.getId());
        assertTrue(trainingStorage.containsKey(training.getId()));

    }

    @Test
    public void givenTrainingExist_whenSelectTraining_thenSuccess() {

        trainingStorage.put(trainingId, training);

        Training actualTraining = trainingDaoImpl.selectTrainingById(trainingId);

        verify(storage).getTrainingStorage();
        assertNotNull(actualTraining);
        assertEquals(training, actualTraining);
    }

    @Test
    public void givenTrainingNotExist_whenSelectTraining_thenReturnNull() {

        Training actualTraining = trainingDaoImpl.selectTrainingById(trainingId);

        verify(storage).getTrainingStorage();
        assertNull(actualTraining);
    }
}
