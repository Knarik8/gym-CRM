package epam.gym.dao.hibernateImpl;

import epam.gym.dao.TraineeDao;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Trainee create(Trainee trainee) {
        entityManager.persist(trainee);
        return trainee;
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        return entityManager.merge(trainee);
    }

    @Override
    @Transactional
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Trainee.class, id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Trainee trainee = entityManager.find(Trainee.class, id);
        if (trainee != null) {
            entityManager.remove(trainee);
        }
    }

    @Override
    public Set<String> getExistingUsernames() {
        List<String> usernames = entityManager.createQuery("SELECT username FROM Trainee", String.class).getResultList();
        return new HashSet<>(usernames);
    }

    @Override
    @Transactional
    public Optional<Trainee> selectTraineeByUsername(String username) {
        try {
            Trainee trainee = entityManager.createQuery("SELECT t FROM Trainee t LEFT JOIN FETCH t.trainings WHERE " +
                            "t.username = :username", Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(trainee);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Trainee> changePassword(Long id, String newPassword) {
        Trainee trainee = entityManager.find(Trainee.class, id);
        if (trainee != null) {
            trainee.setPassword(newPassword);
            entityManager.merge(trainee);
            return Optional.of(trainee);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void setActiveStatus(Long id, boolean isActive) {
        Trainee trainee = entityManager.find(Trainee.class, id);
        if (trainee != null) {
            trainee.setActive(isActive);
            entityManager.merge(trainee);
        }
    }

    @Override
    @Transactional
    public boolean deleteByUsername(String username) {
        Optional<Trainee> traineeOptional = selectTraineeByUsername(username);
        if (traineeOptional.isPresent()) {
            entityManager.remove(traineeOptional.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Training> getTrainingsByTraineeUsernameAndTrainerName(String traineeUsername, String trainerName) {
        return entityManager.createQuery(
                        "SELECT tr FROM Training tr " +
                                "JOIN tr.trainee t " +
                                "JOIN tr.trainer trn " +
                                "WHERE t.username = :traineeUsername AND trn.lastName = :trainerName", Training.class)
                .setParameter("traineeUsername", traineeUsername)
                .setParameter("trainerName", trainerName)
                .getResultList();
    }

    @Override
    @Transactional
    public Trainee updateTraineeTrainersList(Long traineeId, Set<Trainer> trainers) {
        Trainee trainee = entityManager.find(Trainee.class, traineeId);
        trainee.setTrainers(trainers);
        entityManager.merge(trainee);
        return trainee;
    }
}
