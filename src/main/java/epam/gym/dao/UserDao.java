package epam.gym.dao;

import epam.gym.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao {

    Optional<User> findByUsername(String username);
    Optional<User> changePassword(Long id, String newPassword);

    boolean existsByUsername(String username);
    User save(User user);
    Set<String> getExistingUsernames();
}
