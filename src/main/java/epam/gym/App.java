package epam.gym;

import epam.gym.config.AppConfig;
import epam.gym.config.JPAConfig;
import epam.gym.entity.Address;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.TrainingType;
import epam.gym.entity.TrainingTypeEntity;
import epam.gym.service.TraineeService;
import epam.gym.service.TrainerService;
import epam.gym.service.TrainingTypeService;
import epam.gym.service.hibernateImpl.TraineeServiceImpl;
import epam.gym.service.hibernateImpl.TrainerServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class App
{
    public static void main( String[] args )
    {

        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class,
                    JPAConfig.class);


// Trainee ---------------------------------------------------------------------------

            // Create Trainee Profile, demonstrate username and password generating

            TraineeService traineeService = context.getBean(TraineeServiceImpl.class);

            Trainee trainee = Trainee.builder()
                    .firstName("Foe")
                    .lastName("Fof")
                    .username(null)
                    .password(null)
                    .isActive(true)
                    .dateOfBirth(LocalDate.of(1993, 2, 4))
                    .build();

            Address address = Address.builder()
                    .city("Moscow")
                    .street("New str")
                    .buildingNumber(580)
                    .apartmentNumber(60)
                    .trainee(trainee)
                    .build();
            trainee.setAddress(address);

            System.out.println("Created trainee: " + traineeService.create(trainee));

            Trainee updatedTrainee = Trainee.builder()
                    .id(trainee.getId())
                    .firstName("Annushkaa123")
                    .lastName("Ivanova")
                    .username(trainee.getUsername())
                    .password(trainee.getPassword())
                    .isActive(true)
                    .dateOfBirth(LocalDate.of(1993, 2, 4))
                    .build();
            System.out.println("Updated trainee: "+ traineeService.update(trainee.getId(), updatedTrainee, updatedTrainee.getUsername(), updatedTrainee.getPassword()));

            System.out.println("Selected trainee: " + traineeService.findById(trainee.getId(), updatedTrainee.getUsername(), updatedTrainee.getPassword()));

            System.out.println("Selected by username :" + traineeService.selectTraineeByUsername(trainee.getUsername()));

//            traineeService.deleteTraineeById(trainee.getId(), trainee.getUsername(), trainee.getPassword());

// Trainer ---------------------------------------------------------------------------


            // Create Trainer Profile, demonstrate username and password generating

            TrainerService trainerService = context.getBean(TrainerServiceImpl.class);
            TrainingTypeService trainingTypeService = context.getBean(TrainingTypeService.class);

            TrainingTypeEntity trainingTypeEntity = TrainingTypeEntity.builder()
                    .trainingTypeName(TrainingType.CARDIO)
                    .build();

            trainingTypeService.create(trainingTypeEntity);

            Trainer trainer = Trainer.builder()
                    .firstName("Petr")
                    .lastName("Petrov")
                    .username(null)
                    .password(null)
                    .isActive(true)
                    .specialization(trainingTypeEntity)
                    .build();

            trainerService.create(trainer);

            System.out.println("Selected trainer by username: " + trainerService.selectTrainerByUsername("Petr.Petrov"));

            System.out.println(traineeService.getTrainingsByTraineeUsernameAndTrainerName(trainee.getUsername(),trainer.getFirstName(), trainee.getId(), trainee.getPassword()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
