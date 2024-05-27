package epam.gym.dao;

import epam.gym.dao.impl.TrainerDaoImpl;
import epam.gym.entity.Trainer;
import epam.gym.entity.TrainingType;
import epam.gym.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    private Map<Long, Trainer> trainerStorage;
    private Trainer trainer;
    private Long trainerId;
    private Random rd;

    @BeforeEach
    public void setUp(){
        trainerStorage = new HashMap<>();
        rd = new Random();
        trainerId = rd.nextLong();
        trainer = new Trainer(trainerId, "Vlad", "Sokolov", "vlads",
                "password", true, TrainingType.CARDIO);
        when(storage.getTrainerStorage()).thenReturn(trainerStorage);
    }

    @Test
    public void whenCreateTrainer_thenSuccess () {
        trainerDaoImpl.createTrainer(trainer);

        verify(storage, times(2)).getTrainerStorage();
        assertEquals(trainerId, trainer.getId());
        assertTrue(trainerStorage.containsKey(trainer.getId()));
    }

    @Test
    public void givenTrainerExist_whenUpdateTrainer_thenSuccess() {

        trainerStorage.put(trainerId, trainer);
        Trainer updatedTrainer = new Trainer(trainerId, "Vladislav", "Sokolov", "vladslava",
                "password", true, TrainingType.CARDIO);

        Trainer actualTrainer = trainerDaoImpl.updateTrainerById(trainerId, updatedTrainer);

        verify(storage, times(2)).getTrainerStorage();
        assertEquals(updatedTrainer.getId(), trainerStorage.get(trainerId).getId());
        assertEquals(trainerId, updatedTrainer.getId());
        assertEquals(updatedTrainer, actualTrainer);
        assertEquals(updatedTrainer.getFirstName(), actualTrainer.getFirstName());
        assertEquals(updatedTrainer.getUsername(), actualTrainer.getUsername());
    }

    @Test
    public void givenTrainerNotExist_whenUpdateTrainer_thenReturnNull() {

        Trainer updatedTrainer = new Trainer(trainerId, "Vladislav", "Sokolov", "vladslava",
                "password", true, TrainingType.CARDIO);

        Trainer actualTrainer = trainerDaoImpl.updateTrainerById(trainerId, updatedTrainer);

        verify(storage, times(1)).getTrainerStorage();
        assertNull(trainerStorage.get(trainerId));
        assertNull(actualTrainer);
    }

    @Test
    public void givenTrainerExist_whenSelectTrainer_thenSuccess() {

        trainerStorage.put(trainerId, trainer);

        Trainer actualTrainer = trainerDaoImpl.selectTrainerById(trainerId);

        verify(storage).getTrainerStorage();
        assertNotNull(actualTrainer);
        assertEquals(trainer, actualTrainer);
    }

    @Test
    public void givenTrainerNotExist_whenSelectTrainer_thenReturnNull() {

        Trainer actualTrainer = trainerDaoImpl.selectTrainerById(trainerId);

        verify(storage).getTrainerStorage();
        assertNull(actualTrainer);
    }
}
