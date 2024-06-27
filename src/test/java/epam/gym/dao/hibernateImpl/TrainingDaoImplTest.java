package epam.gym.dao.hibernateImpl;

import epam.gym.entity.Address;
import epam.gym.entity.DayOfWeek;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;
import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainingDaoImpl trainingDao;

    private Training training;
    private Trainee trainee;
    private Trainer trainer;
    private Address address;
    private TrainingTypeEntity trainingTypeEntity;

    private Set<DayOfWeek> trainingDays;

    @BeforeEach
    void setUp() {
        trainee = Trainee.builder()
                .firstName("Foe")
                .lastName("Fof")
                .username(null)
                .password(null)
                .isActive(true)
                .dateOfBirth(LocalDate.of(1993, 2, 4))
                .build();

        address = Address.builder()
                .city("Moscow")
                .street("New str")
                .buildingNumber(580)
                .apartmentNumber(60)
                .trainee(trainee)
                .build();
        trainee.setAddress(address);

        trainingTypeEntity = TrainingTypeEntity.builder()
                .trainingTypeName(TrainingType.CARDIO)
                .build();

        trainer = Trainer.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .username(null)
                .password(null)
                .isActive(true)
                .specialization(trainingTypeEntity)
                .build();

        trainingDays = new HashSet<>();
        Collections.addAll(trainingDays,DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);

        training = Training.builder()
                .trainer(trainer)
                .trainee(trainee)
                .trainingDays(trainingDays)
                .trainingName("CARDIO")
                .trainingDuration(Duration.ofMinutes(50))
                .trainingType(trainingTypeEntity)
                .build();
    }

    @Test
    void whenCreateTraining_thenSuccess() {

        Training createdTraining = trainingDao.create(training);

        assertNotNull(createdTraining);
        assertEquals(training, createdTraining);
    }

    @Test
    void givenTrainingExist_whenFindById_thenSuccess() {
        when(entityManager.find(Training.class, training.getId())).thenReturn(training);

        Optional<Training> actualTraining = trainingDao.findById(training.getId());

        assertEquals(Optional.of(training), actualTraining);
        verify(entityManager, times(1)).find(Training.class, training.getId());
    }

    @Test
    void givenTrainerNotExist_whenFindById_thenReturnNull() {
        when(entityManager.find(Training.class, training.getId())).thenReturn(null);

        Optional<Training> actualTraining = trainingDao.findById(training.getId());

        assertFalse(actualTraining.isPresent());
        verify(entityManager, times(1)).find(Training.class, training.getId());
    }

}
