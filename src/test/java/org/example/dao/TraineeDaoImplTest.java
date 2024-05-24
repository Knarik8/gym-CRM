package org.example.dao;

import org.example.dao.impl.TraineeDaoImpl;
import org.example.model.entity.Address;
import org.example.model.entity.Trainee;
import org.example.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private UUID traineeId;
    private Trainee trainee;
    private Map<UUID, Trainee> traineeStorage;

    @BeforeEach
    public void setUp(){
        traineeStorage = new HashMap<>();
        address = new Address("Moscow", "New street", 55, 50);
        traineeId = UUID.randomUUID();
        trainee = new Trainee(traineeId, "Anna", "Petrova", "anna",
                "password", true, LocalDate.of(1993,7,7), address);
        when(storage.getTraineeStorage()).thenReturn(traineeStorage);
    }

    @Test
    public void createTest () {

        traineeDaoImpl.create(trainee);

        verify(storage).getTraineeStorage();
        assertEquals(traineeId, trainee.getId());
        assertTrue(traineeStorage.containsKey(trainee.getId()));

    }

    @Test
    @DisplayName("When Trainee exists")
    public void updateWhenTraineeExistsTest() {

        traineeStorage.put(traineeId, trainee);
        Trainee updatedTrainee = new Trainee(traineeId, "Anna", "Ivanova", "anna",
                "updatedPassword", true, LocalDate.of(1993, 7, 7), address);

        Trainee actualTrainee = traineeDaoImpl.update(traineeId, updatedTrainee);

        verify(storage, times(2)).getTraineeStorage();
        assertEquals(updatedTrainee.getId(), traineeStorage.get(traineeId).getId());
        assertEquals(traineeId, updatedTrainee.getId());
        assertEquals(updatedTrainee, actualTrainee);
        assertEquals(updatedTrainee.getPassword(), actualTrainee.getPassword());
    }

    @Test
    @DisplayName("When Trainee not exists")
    public void updateWhenTraineeNotExist(){

        Trainee updatedTrainee = new Trainee(traineeId, "Anna", "Ivanova", "anna",
                "newpassword", true, LocalDate.of(1993, 7, 7), new Address("Moscow", "New street", 55, 50));


        Trainee actualTrainee = traineeDaoImpl.update(traineeId, updatedTrainee);

        verify(storage, times(1)).getTraineeStorage();
        assertNull(traineeStorage.get(traineeId));
        assertNull(actualTrainee);
    }

    @Test
    @DisplayName("When Trainee exists")
    public void selectWhenTraineeExistsTest() {

        traineeStorage.put(traineeId, trainee);

        Trainee actualTrainee = traineeDaoImpl.select(traineeId);

        verify(storage).getTraineeStorage();
        assertNotNull(actualTrainee);
        assertEquals(trainee, actualTrainee);
    }

    @Test
    @DisplayName("When Trainee not exists")
    public void selectWhenTraineeNotExistsTest() {

        Trainee actualTrainee = traineeDaoImpl.select(traineeId);

        verify(storage).getTraineeStorage();
        assertNull(actualTrainee);
    }

    @Test
    @DisplayName("When Trainee exists")
    public void deleteWhenTraineeExists() {
        traineeStorage.put(traineeId, trainee);

        Trainee actualTrainee = traineeDaoImpl.delete(traineeId);

        verify(storage).getTraineeStorage();
        assertEquals(trainee, actualTrainee);
        assertNull(traineeStorage.get(traineeId));
    }

    @Test
    @DisplayName("When Trainee not exists")
    public void testDeleteTraineeNotExists() {
        Trainee actualTrainee = traineeDaoImpl.delete(traineeId);

        verify(storage).getTraineeStorage();
        assertNull(actualTrainee);
    }

}
