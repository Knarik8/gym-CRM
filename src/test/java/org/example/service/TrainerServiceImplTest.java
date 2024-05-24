package org.example.service;

import org.example.dao.impl.TrainerDaoImpl;
import org.example.model.entity.Trainer;
import org.example.model.enums.TrainingType;
import org.example.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceImplTest {

    @Mock
    private TrainerDaoImpl trainerDaoImpl;

    @InjectMocks
    private TrainerServiceImpl trainerServiceImpl;

    private Trainer trainer;
    private UUID trainerId;

    @BeforeEach
    public void setUp() {
        trainerId = UUID.randomUUID();
        trainer = new Trainer(trainerId, "Vlad", "Sokolov", "vlads",
                "password", true, TrainingType.CARDIO);
    }

    @Test
    public void createTest() {
        when(trainerDaoImpl.create(any(Trainer.class))).thenReturn(trainer);

        Trainer createdTrainer = trainerServiceImpl.create(trainer);

        assertNotNull(createdTrainer);
        assertEquals(trainer.getId(), createdTrainer.getId());
        verify(trainerDaoImpl).create(trainer);
    }

    @Test
    public void updateTest() {
        when(trainerDaoImpl.update(any(UUID.class), any(Trainer.class))).thenReturn(trainer);

        Trainer updatedTrainer = trainerServiceImpl.update(trainerId, trainer);

        assertNotNull(updatedTrainer);
        assertEquals(trainer.getId(), updatedTrainer.getId());
        verify(trainerDaoImpl).update(trainerId, trainer);
    }

    @Test
    public void selectTest() {
        when(trainerDaoImpl.select(any(UUID.class))).thenReturn(trainer);

        Trainer selectedTrainer = trainerServiceImpl.select(trainerId);

        assertNotNull(selectedTrainer);
        assertEquals(trainer.getId(), selectedTrainer.getId());
        verify(trainerDaoImpl).select(trainerId);
    }
}
