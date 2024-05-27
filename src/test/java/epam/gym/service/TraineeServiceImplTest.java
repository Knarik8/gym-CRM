package epam.gym.service;

import epam.gym.dao.impl.TraineeDaoImpl;
import epam.gym.entity.Address;
import epam.gym.entity.Trainee;
import epam.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceImplTest {

    @Mock
    private TraineeDaoImpl traineeDaoImpl;

    @InjectMocks
    private TraineeServiceImpl traineeServiceImpl;

    private Trainee trainee;
    private Long traineeId;
    private Random rd;

    @BeforeEach
    public void setUp() {
        Address address = new Address("Moscow", "New street", 55, 50);
        rd = new Random();
        traineeId = rd.nextLong();
        trainee = new Trainee(traineeId, "Anna", "Petrova", "anna",
                "password", true, LocalDate.of(1993,7,7), address);
    }


    @Test
    public void whenCreateTrainee_thenSuccess() {
        when(traineeDaoImpl.createTrainee(any(Trainee.class))).thenReturn(trainee);

        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);

        assertNotNull(createdTrainee);
        assertEquals(trainee.getId(), createdTrainee.getId());
        verify(traineeDaoImpl).createTrainee(trainee);
    }

    @Test
    public void givenTraineeExist_whenUpdateTrainee_thenSuccess() {
        when(traineeDaoImpl.updateTraineeById(any(Long.class), any(Trainee.class))).thenReturn(trainee);

        Trainee updatedTrainee = traineeServiceImpl.updateTraineeById(traineeId, trainee);

        assertNotNull(updatedTrainee);
        assertEquals(trainee.getId(), updatedTrainee.getId());
        verify(traineeDaoImpl).updateTraineeById(traineeId, trainee);
    }

    @Test
    public void givenTraineeExist_whenDeleteTrainee_thenSuccess() {
        when(traineeDaoImpl.deleteTraineeById(any(Long.class))).thenReturn(trainee);

        Trainee deletedTrainee = traineeServiceImpl.deleteTraineeById(traineeId);

        assertNotNull(deletedTrainee);
        assertEquals(trainee.getId(), deletedTrainee.getId());
        verify(traineeDaoImpl).deleteTraineeById(traineeId);
    }

    @Test
    public void givenTraineeExist_whenSelectTrainee_thenSuccess() {
        when(traineeDaoImpl.selectTraineeById(any(Long.class))).thenReturn(trainee);

        Trainee selectedTrainee = traineeServiceImpl.selectTraineeById(traineeId);

        assertNotNull(selectedTrainee);
        assertEquals(trainee.getId(), selectedTrainee.getId());
        verify(traineeDaoImpl).selectTraineeById(traineeId);
    }
}
