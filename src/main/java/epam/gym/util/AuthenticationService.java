package epam.gym.util;

import epam.gym.entity.User;
import epam.gym.exception.AuthenticationException;
import epam.gym.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private UserService userService;

    AuthenticationService(UserService userService){
        this.userService = userService;
    }

    public boolean authenticate(Long id, String username, String password) {
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                return true;
            }
        }
        throw new AuthenticationException("Authentication failed for user: " + username);
    }
}
