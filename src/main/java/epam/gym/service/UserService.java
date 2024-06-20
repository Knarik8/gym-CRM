package epam.gym.service;

import epam.gym.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    boolean changePassword(Long id, String username, String oldPassword, String newPassword);

}
