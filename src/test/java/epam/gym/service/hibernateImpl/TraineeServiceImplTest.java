package epam.gym.service.hibernateImpl;

import epam.gym.dao.TraineeDao;
import epam.gym.dao.hibernateImpl.TraineeDaoImpl;
import epam.gym.entity.Address;
import epam.gym.entity.Trainee;
import epam.gym.util.AuthenticationService;
import epam.gym.util.ProfileGenerationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private ProfileGenerationHelper profileGenerationHelper;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private Logger logger;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee trainee;
    private Address address;
    Set<String> existingUsernames;
    private String existingUsername;
    private String existingPassword;


    @BeforeEach
    void setUp(){

        trainee = Trainee.builder()
                .firstName("Foe")
                .lastName("Fof")
                .username(null)
                .password(null)
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

//        traineeService = new TraineeServiceImpl(traineeDao, authenticationService);
        existingUsername = "Foe.Fof";
        existingUsernames = new HashSet<>();
        existingUsernames.add(existingUsername);
        existingPassword = "password";
    }

    @Test
    void create(){
//        when(traineeDao.getExistingUsernames()).thenReturn(existingUsernames);
//        when(traineeDao.create(any(Trainee.class))).thenReturn(trainee);
//
//
        Trainee createdTrainee = traineeService.create(trainee);
//
//        assertEquals(trainee, createdTrainee);

        verify(traineeDao).create(trainee);

        // Assert that the trainee's username and password were set correctly
        assertEquals(existingUsername, createdTrainee.getUsername());
        assertEquals(existingPassword, createdTrainee.getPassword());


        // Mock behavior of traineeDao.getExistingUsernames()
        when(traineeDao.getExistingUsernames()).thenReturn(existingUsernames);

        // Mock behavior of ProfileGenerationHelper.generateUsername()
        when(ProfileGenerationHelper.generateUsername(anyString(), anyString(), anySet()))
                .thenReturn(existingUsername);

        // Mock behavior of ProfileGenerationHelper.generatePassword()
        when(ProfileGenerationHelper.generatePassword()).thenReturn(existingPassword);

        // Mock behavior of traineeDao.create()
        when(traineeDao.create(any(Trainee.class))).thenReturn(trainee);

        // Call the method under test
//        Trainee createdTrainee = traineeService.create(trainee);

        // Verify that the create method was called with the correct arguments
        verify(traineeDao).create(trainee);

        // Assert that the trainee's username and password were set correctly
        assertEquals(existingUsername, createdTrainee.getUsername());
        assertEquals(existingPassword, createdTrainee.getPassword());
    }

}
