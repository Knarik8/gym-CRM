package epam.gym.service.hibernateImpl;

import epam.gym.dao.UserDao;
import epam.gym.entity.User;
import epam.gym.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
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
            Optional<User> updatedUser = userDao.changePassword(id, newPassword);
            if (updatedUser.isPresent()) {
                logger.info("Password changed successfully for user with ID: {}", id);
            } else {
                logger.warn("Failed to change password. User with ID: {} not found.", id);
            }
            return true;
    }
}
