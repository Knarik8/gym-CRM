package epam.gym.service.hibernateImpl;

import epam.gym.dao.UserDao;
import epam.gym.entity.User;
import epam.gym.exception.AuthenticationException;
import epam.gym.service.UserService;
import epam.gym.util.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;
    private AuthenticationService authenticationService;

    UserServiceImpl(UserDao userDao, @Lazy AuthenticationService authenticationService){
        this.userDao = userDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> userOptional = userDao.findByUsername(username);
        if (userOptional.isPresent()) {
            logger.info("User found with username: {}", username);
        } else {
            logger.warn("User not found with username: {}", username);
        }
        return userOptional;
    }

    @Override
    public boolean changePassword(Long id, String username, String oldPassword, String newPassword) {
        if (authenticationService.authenticate(id, username, oldPassword)) {
            Optional<User> updatedUser = userDao.changePassword(id, newPassword);
            if (updatedUser.isPresent()) {
                logger.info("Password changed successfully for user with ID: {}", id);
            } else {
                logger.warn("Failed to change password. User with ID: {} not found.", id);
            }
            return true;
        } else {
            logger.error("Authentication failed for user: {}", username);
            throw new AuthenticationException("Authentication failed for user: " + username);
        }
    }
}
