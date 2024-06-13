package epam.gym.dao.hibernateImpl;

import epam.gym.dao.TrainingTypeDao;
import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public TrainingTypeEntity create(TrainingTypeEntity trainingTypeEntity) {
        entityManager.persist(trainingTypeEntity);
        return trainingTypeEntity;
    }

    @Override
    public TrainingTypeEntity findByName(TrainingType trainingType) {
        TypedQuery<TrainingTypeEntity> query = entityManager.createQuery(
                "SELECT FROM training_type WHERE t.trainingTypeName = :name", TrainingTypeEntity.class);
        query.setParameter("name", trainingType);
        return query.getSingleResult();

    }
}
