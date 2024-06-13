package epam.gym.service.hibernateImpl;

import epam.gym.dao.TrainingTypeDao;
import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {
    @Mock
    private TrainingTypeDao trainingTypeDao;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    private TrainingTypeEntity trainingTypeEntity;

    @BeforeEach
    void setUp() {
        trainingTypeEntity = TrainingTypeEntity.builder()
                .trainingTypeName(TrainingType.CARDIO)
                .build();

    }

    @Test
    void whenCreateTrainingTypeEntity_thenSuccess() {
        when(trainingTypeDao.create(any(TrainingTypeEntity.class))).thenReturn(trainingTypeEntity);

        TrainingTypeEntity createdTrainingTypeEntity = trainingTypeService.create(trainingTypeEntity);

        assertEquals(trainingTypeEntity, createdTrainingTypeEntity);
    }

    @Test
    void givenTrainingTypeExist_whenSelectByName_thenSuccess() {
        when(trainingTypeDao.findByName(any(TrainingType.class))).thenReturn(trainingTypeEntity);

        TrainingTypeEntity result = trainingTypeService.findByName(trainingTypeEntity.getTrainingTypeName());

        assertEquals(trainingTypeEntity, result);
    }

    @Test
    void givenTrainingTypeNotExist_whenSelectByName_thenReturnNull() {
        when(trainingTypeDao.findByName(any(TrainingType.class))).thenReturn(null);

        TrainingTypeEntity result = trainingTypeService.findByName(trainingTypeEntity.getTrainingTypeName());

        assertNull(result);
    }

}
