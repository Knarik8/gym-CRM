package org.example.dao;

import org.example.dao.impl.TrainerDaoImpl;
import org.example.model.entity.Trainer;
import org.example.model.enums.TrainingType;
import org.example.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerDaoImplTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TrainerDaoImpl trainerDaoImpl;

    private Map<UUID, Trainer> trainerStorage;
    private Trainer trainer;
    private UUID trainerId;

    @BeforeEach
    public void setUp(){
        trainerStorage = new HashMap<>();
        trainerId = UUID.randomUUID();
        trainer = new Trainer(trainerId, "Vlad", "Sokolov", "vlads",
                "password", true, TrainingType.CARDIO);
        when(storage.getTrainerStorage()).thenReturn(trainerStorage);
    }

    @Test
    public void createTest () {
        trainerDaoImpl.create(trainer);

        verify(storage).getTrainerStorage();
        assertEquals(trainerId, trainer.getId());
        assertTrue(trainerStorage.containsKey(trainer.getId()));
    }

    @Test
    @DisplayName("When Trainer exists")
    public void updateWhenTrainerExistsTest() {

        trainerStorage.put(trainerId, trainer);
        Trainer updatedTrainer = new Trainer(trainerId, "Vladislav", "Sokolov", "vladslava",
                "password", true, TrainingType.CARDIO);

        Trainer actualTrainer = trainerDaoImpl.update(trainerId, updatedTrainer);

        verify(storage, times(2)).getTrainerStorage();
        assertEquals(updatedTrainer.getId(), trainerStorage.get(trainerId).getId());
        assertEquals(trainerId, updatedTrainer.getId());
        assertEquals(updatedTrainer, actualTrainer);
        assertEquals(updatedTrainer.getFirstName(), actualTrainer.getFirstName());
        assertEquals(updatedTrainer.getUsername(), actualTrainer.getUsername());
    }

    @Test
    @DisplayName("When Trainer not exists")
    public void updateWhenTrainerNotExistsTest() {

        Trainer updatedTrainer = new Trainer(trainerId, "Vladislav", "Sokolov", "vladslava",
                "password", true, TrainingType.CARDIO);

        Trainer actualTrainer = trainerDaoImpl.update(trainerId, updatedTrainer);

        verify(storage, times(1)).getTrainerStorage();
        assertNull(trainerStorage.get(trainerId));
        assertNull(actualTrainer);
    }

    @Test
    @DisplayName("When Trainer exists")
    public void selectWhenTrainerExistsTest() {

        trainerStorage.put(trainerId, trainer);

        Trainer actualTrainer = trainerDaoImpl.select(trainerId);

        verify(storage).getTrainerStorage();
        assertNotNull(actualTrainer);
        assertEquals(trainer, actualTrainer);
    }

    @Test
    @DisplayName("When Trainer not exists")
    public void selectWhenTrainerNotExistsTest() {

        Trainer actualTrainer = trainerDaoImpl.select(trainerId);

        verify(storage).getTrainerStorage();
        assertNull(actualTrainer);
    }
}
