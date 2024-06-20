package epam.gym.dao;

import epam.gym.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findByUsername(String username);
    Optional<User> changePassword(Long id, String newPassword);
}
