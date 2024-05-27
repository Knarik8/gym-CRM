package epam.gym;

import epam.gym.config.AppConfig;
import epam.gym.entity.Address;
import epam.gym.entity.Trainee;
import epam.gym.service.TraineeService;
import epam.gym.service.impl.TraineeServiceImpl;
import epam.gym.storage.InMemoryStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Random;

public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        InMemoryStorage inMemoryStorage = context.getBean( InMemoryStorage.class);

        // Demonstrates point №3
        System.out.println("Trainee Storage: " + inMemoryStorage.getTraineeStorage());
        System.out.println("Trainer Storage: " + inMemoryStorage.getTrainerStorage());
        System.out.println("Training Storage: " + inMemoryStorage.getTrainingStorage());
        System.out.println();

        // Demonstrates point №7 (generating username and password)
        TraineeService traineeServiceImpl = context.getBean(TraineeServiceImpl.class);

        Random rd = new Random();
        Long id = rd.nextLong();
        String firstName = "Anna";
        String lastName = "Petrova";
        LocalDate dateOfBirth = LocalDate.of(1993, 2, 4);
        Address address = new Address("Moscow", "New street", 50, 55);
        Trainee trainee = new Trainee(id, firstName, lastName, null, null, true, dateOfBirth, address);

        Trainee createdTrainee = traineeServiceImpl.createTrainee(trainee);
        System.out.println("Created Trainee: " + createdTrainee);

    }
}
