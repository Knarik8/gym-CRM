package epam.gym.dao;

import epam.gym.entity.Trainee;
import epam.gym.dao.impl.TraineeDaoImpl;
import epam.gym.entity.Address;
import epam.gym.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TraineeDaoImplTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TraineeDaoImpl traineeDaoImpl;

    private Address address;
    private Long traineeId;
    private Trainee trainee;
    private Map<Long, Trainee> traineeStorage;
    private Random rd;

    @BeforeEach
    public void setUp(){
        traineeStorage = new HashMap<>();
        address = new Address("Moscow", "New street", 55, 50);
        rd = new Random();
        traineeId = rd.nextLong();
        trainee = new Trainee(traineeId, "Anna", "Petrova", "anna",
                "password", true, LocalDate.of(1993,7,7), address);
        when(storage.getTraineeStorage()).thenReturn(traineeStorage);
    }

    @Test
    public void whenCreateTrainee_thenSuccess () {

        traineeDaoImpl.createTrainee(trainee);

        verify(storage, times(2)).getTraineeStorage();
        assertEquals(traineeId, trainee.getId());
        assertTrue(traineeStorage.containsKey(trainee.getId()));

    }

    @Test
    public void givenTraineeExist_whenUpdateTrainee_thenSuccess() {

        traineeStorage.put(traineeId, trainee);
        Trainee updatedTrainee = new Trainee(traineeId, "Anna", "Ivanova", "anna",
                "updatedPassword", true, LocalDate.of(1993, 7, 7), address);

        Trainee actualTrainee = traineeDaoImpl.updateTraineeById(traineeId, updatedTrainee);

        verify(storage, times(2)).getTraineeStorage();
        assertEquals(updatedTrainee.getId(), traineeStorage.get(traineeId).getId());
        assertEquals(traineeId, updatedTrainee.getId());
        assertEquals(updatedTrainee, actualTrainee);
        assertEquals(updatedTrainee.getPassword(), actualTrainee.getPassword());
    }

    @Test
    public void givenTraineeNotExist_whenUpdateTrainee_thenReturnNull(){

        Trainee updatedTrainee = new Trainee(traineeId, "Anna", "Ivanova", "anna",
                "newpassword", true, LocalDate.of(1993, 7, 7), new Address(
                        "Moscow", "New street", 55, 50));


        Trainee actualTrainee = traineeDaoImpl.updateTraineeById(traineeId, updatedTrainee);

        verify(storage, times(1)).getTraineeStorage();
        assertNull(traineeStorage.get(traineeId));
        assertNull(actualTrainee);
    }

    @Test
    public void givenTraineeExist_whenSelectTrainee_thenSuccess() {

        traineeStorage.put(traineeId, trainee);

        Trainee actualTrainee = traineeDaoImpl.selectTraineeById(traineeId);

        verify(storage).getTraineeStorage();
        assertNotNull(actualTrainee);
        assertEquals(trainee, actualTrainee);
    }

    @Test
    public void givenTraineeNotExist_whenSelectTrainee_thenReturnNull() {

        Trainee actualTrainee = traineeDaoImpl.selectTraineeById(traineeId);

        verify(storage).getTraineeStorage();
        assertNull(actualTrainee);
    }

    @Test
    public void givenTraineeExist_whenDeleteTrainee_thenSuccess() {
        traineeStorage.put(traineeId, trainee);

        Trainee actualTrainee = traineeDaoImpl.deleteTraineeById(traineeId);

        verify(storage).getTraineeStorage();
        assertEquals(trainee, actualTrainee);
        assertNull(traineeStorage.get(traineeId));
    }

    @Test
    public void givenTraineeNotExist_whenDeleteTrainee_thenReturnNull() {
        Trainee actualTrainee = traineeDaoImpl.deleteTraineeById(traineeId);

        verify(storage).getTraineeStorage();
        assertNull(actualTrainee);
    }

}
