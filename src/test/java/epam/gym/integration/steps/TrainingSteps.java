package epam.gym.integration.steps;

import epam.gym.dto.trainer.TrainerWorkloadInfo;
import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.Training;
import epam.gym.service.TrainingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TrainingSteps {

    private final TrainingService trainingService;
    private String trainerUsername;

    private Training createdTraining;
    private TrainingDto trainingDto;


    private final Queue destination;

    private final JmsTemplate jmsTemplate;

    private final RestTemplate restTemplate;

    TrainingSteps(TrainingService trainingService, Queue destination, RestTemplate restTemplate, JmsTemplate jmsTemplate) {
        this.trainingService = trainingService;
        this.destination = destination;
        this.restTemplate = restTemplate;
        this.jmsTemplate = jmsTemplate;
    }

    @Given("a new training with trainee ID {string}, trainer ID {string} and training type ID {string} and date {string} and trainingDuration {string}")
    public void aNewTraining(String traineeId, String trainerId, String trainingTypeId, String date, String trainingDuration) {
        trainingDto = new TrainingDto();
        trainingDto.setTraineeId(Long.parseLong(traineeId));
        trainingDto.setTrainerId(Long.parseLong(trainerId));
        trainingDto.setTrainingTypeId(Long.parseLong(trainingTypeId));
        trainingDto.setTrainingDate(LocalDateTime.parse(date));
        trainingDto.setTrainingDuration(Long.valueOf(trainingDuration));
        createdTraining = trainingService.create(trainingDto);

        assertNotNull(createdTraining, "Created training should not be null");
        trainerUsername = createdTraining.getTrainer().getUsername();

    }

    @When("I send a request to create the training")
    public void iSendARequestToCreateTheTraining() {
        assertNotNull(createdTraining);
    }

    @Then("the training should be created with ID {string}")
    public void theTrainingShouldBeCreated(String id) {
        assertNotNull(createdTraining);
        assertNotNull(createdTraining.getId());
    }


    @Then("the trainer's workload should be updated in the reporting microservice")
    public void theTrainerWorkloadShouldBeUpdatedInReportingMicroservice() {
//        Message message = jmsTemplate.receive(destination);//
//
//        assertNotNull(message, "Message should be received");
//
//        TrainerWorkload trainerWorkload;
//        if (message instanceof ObjectMessage) {
//            ObjectMessage objectMessage = (ObjectMessage) message;
//            trainerWorkload = (TrainerWorkload) objectMessage.getObject();
//            assertNotNull(trainerWorkload, "TrainerWorkload should not be null");

//            assertEquals(trainingDto.getTrainingDuration(), trainerWorkload.getTrainingDuration(), "Training duration does not match");
//            assertEquals(trainingDto.getTrainingDate(), trainerWorkload.getTrainingDate(), "Training date does not match");
//        } else {
//            fail("Expected an ObjectMessage but received " + message.getClass().getName());
//        }


        // verifying the trainer's workload in the Reporting microservice
        ResponseEntity<TrainerWorkloadInfo> response = restTemplate.exchange(
                "http://localhost:8081/reports/trainers/" + trainerUsername,
                HttpMethod.GET,
                null,
                TrainerWorkloadInfo.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status code does not match expected");

        TrainerWorkloadInfo trainerWorkloadInfo = response.getBody();
        assertNotNull(trainerWorkloadInfo, "TrainerWorkloadInfo should not be null");
    }
}
