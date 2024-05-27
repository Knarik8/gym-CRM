package epam.gym.service;

import epam.gym.dao.impl.TrainingDaoImpl;
import epam.gym.entity.Training;
import epam.gym.service.impl.TrainingServiceImpl;
import epam.gym.entity.DayOfWeek;
import epam.gym.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Set;
import java.util.Random;
import java.util.HashSet;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest {

    @Mock
    private TrainingDaoImpl trainingDaoImpl;

    @InjectMocks
    private TrainingServiceImpl trainingServiceImpl;

    private Training training;
    private Long trainingId;
    private Set<DayOfWeek> trainingDays;
    private Random rd;


    @BeforeEach
    public void setUp() {
        rd = new Random();
        trainingId = rd.nextLong();
        trainingDays = new HashSet<>();
        Collections.addAll(trainingDays, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        training = new Training(trainingId, rd.nextLong(), "Cardio training", TrainingType.CARDIO,
                trainingDays, Duration.ofMinutes(50));
    }

    @Test
    public void whenCreateTraining_thenSuccess() {
        when(trainingDaoImpl.createTraining(any(Training.class))).thenReturn(training);

        Training createdTraining = trainingServiceImpl.createTraining(training);

        assertNotNull(createdTraining);
        assertEquals(training.getId(), createdTraining.getId());
        verify(trainingDaoImpl).createTraining(training);
    }

    @Test
    public void givenTrainingExist_whenSelectTraining_thenSuccess() {
        when(trainingDaoImpl.selectTrainingById(any(Long.class))).thenReturn(training);

        Training selectedTraining = trainingServiceImpl.selectTrainingById(trainingId);

        assertNotNull(selectedTraining);
        assertEquals(training.getId(), selectedTraining.getId());
        verify(trainingDaoImpl).selectTrainingById(trainingId);
    }
}
