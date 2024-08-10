package epam.gym.dao.hibernateImpl;

import epam.gym.dao.UserDao;
import epam.gym.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public boolean existsByUsername(String username) {
        Long count = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional
    public User save(@NonNull User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Set<String> getExistingUsernames() {
        List<String> usernames = entityManager.createQuery("SELECT username FROM User", String.class).getResultList();
        return new HashSet<>(usernames);
    }

}
