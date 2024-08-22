package epam.gym.service;

import epam.gym.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService {

    User findByUsername(String username);

    boolean changePassword(Long id, String username, String oldPassword, String newPassword);

    User save(User user);

    UserDetailsService userDetailsService();

    Set<String> getExistingUsernames();



}
