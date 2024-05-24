package org.example.service;

import org.example.dao.impl.TraineeDaoImpl;
import org.example.model.entity.Address;
import org.example.model.entity.Trainee;
import org.example.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
    private UUID traineeId;

    @BeforeEach
    public void setUp() {
        Address address = new Address("Moscow", "New street", 55, 50);
        traineeId = UUID.randomUUID();
        trainee = new Trainee(traineeId, "Anna", "Petrova", "anna",
                "password", true, LocalDate.of(1993,7,7), address);
    }


    @Test
    public void createTest() {
        when(traineeDaoImpl.create(any(Trainee.class))).thenReturn(trainee);

        Trainee createdTrainee = traineeServiceImpl.create(trainee);

        assertNotNull(createdTrainee);
        assertEquals(trainee.getId(), createdTrainee.getId());
        verify(traineeDaoImpl).create(trainee);
    }

    @Test
    public void updateTest() {
        when(traineeDaoImpl.update(any(UUID.class), any(Trainee.class))).thenReturn(trainee);

        Trainee updatedTrainee = traineeServiceImpl.update(traineeId, trainee);

        assertNotNull(updatedTrainee);
        assertEquals(trainee.getId(), updatedTrainee.getId());
        verify(traineeDaoImpl).update(traineeId, trainee);
    }

    @Test
    public void testDeleteTrainee() {
        when(traineeDaoImpl.delete(any(UUID.class))).thenReturn(trainee);

        Trainee deletedTrainee = traineeServiceImpl.delete(traineeId);

        assertNotNull(deletedTrainee);
        assertEquals(trainee.getId(), deletedTrainee.getId());
        verify(traineeDaoImpl).delete(traineeId);
    }

    @Test
    public void testSelectTrainee() {
        when(traineeDaoImpl.select(any(UUID.class))).thenReturn(trainee);

        Trainee selectedTrainee = traineeServiceImpl.select(traineeId);

        assertNotNull(selectedTrainee);
        assertEquals(trainee.getId(), selectedTrainee.getId());
        verify(traineeDaoImpl).select(traineeId);
    }
}
