package epam.gym.dao.hibernateImpl;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
 class TrainingTypeDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery query;
    @InjectMocks
    private TrainingTypeDaoImpl trainingTypeDao;

    TrainingTypeEntity trainingTypeEntity;



    @BeforeEach
    void setUp() {
        trainingTypeEntity = TrainingTypeEntity.builder()
                .trainingTypeName(TrainingType.CARDIO)
                .build();

    }

    @Test
    void whenCreateTrainingTypeEntity_thenSuccess() {

        TrainingTypeEntity createdTrainingTypeEntity = trainingTypeDao.create(trainingTypeEntity);

        assertNotNull(createdTrainingTypeEntity);
        assertEquals(trainingTypeEntity, createdTrainingTypeEntity);
    }


}
