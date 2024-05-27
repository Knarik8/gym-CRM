package epam.gym.service;

import epam.gym.dao.impl.TrainerDaoImpl;
import epam.gym.entity.Trainer;
import epam.gym.entity.TrainingType;
import epam.gym.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
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
    private Long trainerId;
    private Random rd;

    @BeforeEach
    public void setUp() {
        rd = new Random();
        trainerId = rd.nextLong();
        trainer = new Trainer(trainerId, "Vlad", "Sokolov", "vlads",
                "password", true, TrainingType.CARDIO);
    }

    @Test
    public void whenCreateTrainer_thenSuccess() {
        when(trainerDaoImpl.createTrainer(any(Trainer.class))).thenReturn(trainer);

        Trainer createdTrainer = trainerServiceImpl.createTrainer(trainer);

        assertNotNull(createdTrainer);
        assertEquals(trainer.getId(), createdTrainer.getId());
        verify(trainerDaoImpl).createTrainer(trainer);
    }

    @Test
    public void givenTrainerExist_whenUpdateTrainer_thenSuccess() {
        when(trainerDaoImpl.updateTrainerById(any(Long.class), any(Trainer.class))).thenReturn(trainer);

        Trainer updatedTrainer = trainerServiceImpl.updateTrainerById(trainerId, trainer);

        assertNotNull(updatedTrainer);
        assertEquals(trainer.getId(), updatedTrainer.getId());
        verify(trainerDaoImpl).updateTrainerById(trainerId, trainer);
    }

    @Test
    public void givenTrainerExist_whenSelectTrainer_thenSuccess() {
        when(trainerDaoImpl.selectTrainerById(any(Long.class))).thenReturn(trainer);

        Trainer selectedTrainer = trainerServiceImpl.selectTrainerById(trainerId);

        assertNotNull(selectedTrainer);
        assertEquals(trainer.getId(), selectedTrainer.getId());
        verify(trainerDaoImpl).selectTrainerById(trainerId);
    }
}
