package org.example.dao;

import org.example.dao.impl.TrainingDaoImpl;
import org.example.model.entity.Training;
import org.example.model.enums.DayOfWeek;
import org.example.model.enums.TrainingType;
import org.example.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

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

    private Map<UUID, Training> trainingStorage;
    private Training training;
    private UUID trainingId;
    private Set<DayOfWeek> trainingDays;

    @BeforeEach
    public void setUp(){
        trainingStorage = new HashMap<>();
        trainingId = UUID.randomUUID();
        trainingDays = new HashSet<>();
        Collections.addAll(trainingDays,DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);

        training = new Training(trainingId, UUID.randomUUID(), "Cardio training", TrainingType.CARDIO,
                trainingDays, Duration.ofMinutes(50));
        when(storage.getTrainingStorage()).thenReturn(trainingStorage);
    }

    @Test
    public void createTest () {

        trainingDaoImpl.create(training);

        verify(storage).getTrainingStorage();
        assertEquals(trainingId, training.getId());
        assertTrue(trainingStorage.containsKey(training.getId()));

    }

    @Test
    @DisplayName("When Training exists")
    public void selectWhenTrainingExistsTest() {

        trainingStorage.put(trainingId, training);

        Training actualTraining = trainingDaoImpl.select(trainingId);

        verify(storage).getTrainingStorage();
        assertNotNull(actualTraining);
        assertEquals(training, actualTraining);
    }

    @Test
    @DisplayName("When Training not exists")
    public void selectWhenTrainingNotExistsTest() {

        Training actualTraining = trainingDaoImpl.select(trainingId);

        verify(storage).getTrainingStorage();
        assertNull(actualTraining);
    }
}
