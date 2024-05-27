package epam.gym.storage;

import epam.gym.entity.Address;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.Training;
import epam.gym.entity.DayOfWeek;
import epam.gym.entity.TrainingType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final InMemoryStorage storage;

    @Value("${data.file.path}")
    private String dataFilePath;

    public DataInitializer(InMemoryStorage storage) {
        this.storage = storage;
    }

    @PostConstruct
    public void init() {
        JSONParser jsonParser = new JSONParser();
        System.out.println(dataFilePath);

        try (FileReader reader = new FileReader(dataFilePath)) {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray traineesArray = (JSONArray) obj.get("trainees");
            JSONArray trainersArray = (JSONArray) obj.get("trainers");
            JSONArray trainingsArray = (JSONArray) obj.get("trainings");

            for (Object o : traineesArray) {
                JSONObject traineeObject = (JSONObject) o;

                String idString = (String) traineeObject.get("id");
                Long id =  Long.parseLong(idString);
                String firstName = (String) traineeObject.get("firstName");
                String lastName = (String) traineeObject.get("lastName");
                String username = (String) traineeObject.get("username");
                String password = (String) traineeObject.get("password");
                boolean isActive = (Boolean) traineeObject.get("isActive");
                String dateOfBirthString = (String) traineeObject.get("dateOfBirth");
                LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString);

                JSONObject addressObject = (JSONObject) traineeObject.get("address");
                String city = (String) addressObject.get("city");
                String street = (String) addressObject.get("street");
                int houseNumber = ((Long) addressObject.get("houseNumber")).intValue();
                int apartmentNumber = ((Long) addressObject.get("apartmentNumber")).intValue();

                Address address = new Address(city, street, houseNumber, apartmentNumber);
                Trainee trainee = new Trainee(id, firstName, lastName, username, password, isActive, dateOfBirth, address);
                storage.getTraineeStorage().put(id, trainee);
                logger.info("Loaded trainee with ID: {}", id);
            }

            for (Object o : trainersArray) {
                JSONObject trainerObject = (JSONObject) o;

                String idString = (String) trainerObject.get("id");
                Long id =  Long.parseLong(idString);
                String firstName = (String) trainerObject.get("firstName");
                String lastName = (String) trainerObject.get("lastName");
                String username = (String) trainerObject.get("username");
                String password = (String) trainerObject.get("password");
                boolean isActive = (Boolean) trainerObject.get("isActive");
                String specializationString = (String) trainerObject.get("specialization");

                TrainingType specialization = TrainingType.valueOf(specializationString);

                Trainer trainer = new Trainer(id, firstName, lastName, username, password, isActive, specialization);
                storage.getTrainerStorage().put(id, trainer);
                logger.info("Loaded trainer with ID: {}", id);
            }

            for (Object o : trainingsArray) {
                JSONObject trainingObject = (JSONObject) o;

                String idString = (String) trainingObject.get("id");
                Long id =  Long.parseLong(idString);

                String trainerIdString = (String) trainingObject.get("trainerId");
                Long trainerId =  Long.parseLong(trainerIdString);

                String trainingName = (String) trainingObject.get("trainingName");
                String trainingTypeString = (String) trainingObject.get("trainingType");
                TrainingType trainingType = TrainingType.valueOf(trainingTypeString);
                JSONArray trainingDaysArray = (JSONArray) trainingObject.get("trainingDays");
                String trainingDurationString = (String) trainingObject.get("trainingDuration");
                Duration trainingDuration = Duration.parse(trainingDurationString);

                Set<DayOfWeek> trainingDays = new HashSet<>();
                for (Object day : trainingDaysArray) {
                    trainingDays.add(DayOfWeek.valueOf(((String) day).toUpperCase()));
                }

                Training training = new Training(id, trainerId, trainingName, trainingType, trainingDays, trainingDuration);
                storage.getTrainingStorage().put(id, training);
                logger.info("Loaded training with ID: {}", id);
            }

        } catch (IOException | ParseException e) {
            logger.error("Failed to initialize data from file: {}", dataFilePath, e);
        }
    }
}
