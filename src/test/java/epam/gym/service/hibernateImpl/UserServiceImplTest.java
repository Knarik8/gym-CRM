package epam.gym.service.hibernateImpl;

import epam.gym.dao.UserDao;
import epam.gym.entity.Address;
import epam.gym.entity.Trainee;
import epam.gym.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserServiceImpl userService;

    private Trainee trainee;
    private Address address;

    @BeforeEach
    void setUp() {
        trainee = Trainee.builder()
                .id(1L)
                .firstName("Foe")
                .lastName("Fof")
                .username("username")
                .password("password")
                .isActive(true)
                .dateOfBirth(LocalDate.of(1993, 2, 4))
                .build();

        address = Address.builder()
                .city("Moscow")
                .street("New str")
                .buildingNumber(580)
                .apartmentNumber(60)
                .trainee(trainee)
                .build();
        trainee.setAddress(address);
    }

    @Test
    void givenTraineeExist_whenFindByUsername_thenSuccess(){
        when(userDao.findByUsername(trainee.getUsername())).thenReturn(Optional.of(trainee));

        Optional<User> userOptional = userService.findByUsername(trainee.getUsername());

        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get(), trainee);
    }

    @Test
    void givenTraineeNotExist_whenFindByUsername_thenReturnNull() {
        when(userDao.findByUsername(trainee.getUsername())).thenReturn(Optional.empty());

        Optional<User> userOptional = userService.findByUsername(trainee.getUsername());

        assertFalse(userOptional.isPresent());
    }
}
