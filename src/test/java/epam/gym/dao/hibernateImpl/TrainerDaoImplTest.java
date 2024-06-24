package epam.gym.dao.hibernateImpl;

import epam.gym.entity.Address;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery query;

    @InjectMocks
    private TrainerDaoImpl trainerDao;

    private Trainer trainer;

    private TrainingTypeEntity trainingTypeEntity;

    @BeforeEach
    void setUp() {

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

    }

    @Test
    void whenCreateTrainee_thenSuccess() {

        Trainer createdTrainer = trainerDao.create(trainer);

        assertNotNull(createdTrainer);
        assertEquals(trainer, createdTrainer);
    }

    @Test
    void givenTrainerExist_whenUpdateTrainer_thenSuccess() {
        Trainer updatedTrainer = Trainer.builder()
                .firstName("Anna")
                .lastName("Petrova")
                .username(null)
                .password(null)
                .isActive(true)
                .specialization(trainingTypeEntity)
                .build();
        when(entityManager.merge(trainer)).thenReturn(updatedTrainer);

        Trainer actualTrainer = trainerDao.update(trainer);

        verify(entityManager).merge(trainer);
        assertEquals(actualTrainer, updatedTrainer);
    }

    @Test
    void givenTrainerExist_whenFindById_thenSuccess() {
        when(entityManager.find(Trainer.class, trainer.getId())).thenReturn(trainer);

        Optional<Trainer> actualTrainer = trainerDao.findById(trainer.getId());

        assertEquals(Optional.of(trainer), actualTrainer);
        verify(entityManager, times(1)).find(Trainer.class, trainer.getId());
    }

    @Test
    void givenTrainerNotExist_whenFindById_thenReturnNull() {
        when(entityManager.find(Trainer.class, trainer.getId())).thenReturn(null);

        Optional<Trainer> actualTrainer = trainerDao.findById(trainer.getId());

        assertFalse(actualTrainer.isPresent());
        verify(entityManager, times(1)).find(Trainer.class, trainer.getId());
    }


//    @Test
//    void givenTrainerExisted_whenChangePassword_thenSuccess(){
//        String newPassword = "newPassword";
//        when(entityManager.find(Trainer.class, trainer.getId())).thenReturn(trainer);
//        when(entityManager.merge(trainer)).thenReturn(trainer);
//
//        Optional<Trainer> actualTrainer = trainerDao.changePassword(trainer.getId(), newPassword);
//
//        assertTrue(actualTrainer.isPresent());
//        assertEquals(newPassword, actualTrainer.get().getPassword());
//        verify(entityManager).find(Trainer.class, trainer.getId());
//        verify(entityManager).merge(trainer);
//    }
//
//    @Test
//    void givenTrainerNotExisted_whenChangePassword_thenNotFound(){
//        String newPassword = "newPassword";
//        when(entityManager.find(Trainer.class, trainer.getId())).thenReturn(null);
//
//        Optional<Trainer> actualTrainer = trainerDao.changePassword(trainer.getId(), newPassword);
//
//        assertFalse(actualTrainer.isPresent());
//        verify(entityManager).find(Trainer.class, trainer.getId());
//        verify(entityManager, never()).merge(any(Trainer.class));
//    }

    @Test
    void givenTrainerExisted_whenSetActiveStatus_thenSuccess(){
        boolean isActive = false;
        when(entityManager.find(Trainer.class, trainer.getId())).thenReturn(trainer);
        when(entityManager.merge(any(Trainer.class))).thenReturn(trainer);

        trainerDao.setActiveStatus(trainer.getId(), isActive);

        verify(entityManager).find(Trainer.class, trainer.getId());
        verify(entityManager).merge(trainer);
        assertEquals(isActive, trainer.isActive());
    }

    @Test
    void givenTrainerNotExisted_whenSetActiveStatus_thenNotFound(){
        boolean isActive = false;
        when(entityManager.find(Trainer.class, trainer.getId())).thenReturn(null);

        trainerDao.setActiveStatus(trainer.getId(), isActive);

        verify(entityManager).find(Trainer.class, trainer.getId());
        verify(entityManager, never()).merge(any(Trainer.class));
    }

}
