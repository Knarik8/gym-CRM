package org.example.service;

import org.example.dao.impl.TrainingDaoImpl;
import org.example.model.entity.Training;
import org.example.model.enums.DayOfWeek;
import org.example.model.enums.TrainingType;
import org.example.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private UUID trainingId;
    private Set<DayOfWeek> trainingDays;


    @BeforeEach
    public void setUp() {
        trainingId = UUID.randomUUID();

        trainingDays = new HashSet<>();
        Collections.addAll(trainingDays, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        training = new Training(trainingId, UUID.randomUUID(), "Cardio training", TrainingType.CARDIO,
                trainingDays, Duration.ofMinutes(50));
    }

    @Test
    public void createTest() {
        when(trainingDaoImpl.create(any(Training.class))).thenReturn(training);

        Training createdTraining = trainingServiceImpl.create(training);

        assertNotNull(createdTraining);
        assertEquals(training.getId(), createdTraining.getId());
        verify(trainingDaoImpl).create(training);
    }

    @Test
    public void selectTest() {
        when(trainingDaoImpl.select(any(UUID.class))).thenReturn(training);

        Training selectedTraining = trainingServiceImpl.select(trainingId);

        assertNotNull(selectedTraining);
        assertEquals(training.getId(), selectedTraining.getId());
        verify(trainingDaoImpl).select(trainingId);
    }
}
