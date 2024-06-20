package epam.gym.dao.hibernateImpl;

import epam.gym.dao.UserDao;
import epam.gym.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.of(user);

    }


    @Override
    @Transactional
    public Optional<User> changePassword(Long id, String newPassword) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setPassword(newPassword);
            entityManager.merge(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
