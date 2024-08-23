package epam.gym.controller;

import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.TrainerWorkload;
import epam.gym.entity.Training;
import epam.gym.producer.TrainerWorkloadProducer;
import epam.gym.service.TrainingService;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static epam.gym.mapper.TrainingMapper.trainingMapper;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private TrainingService trainingService;

    @Value("${activemq.destination}")
    private String destination;

    private JmsTemplate jmsTemplate;
    private TrainerWorkloadProducer producer;


    TrainingController(TrainingService trainingService, JmsTemplate jmsTemplate, TrainerWorkloadProducer trainerWorkloadProducer,
                       ObservationRegistry observationRegistry){
        this.trainingService = trainingService;
        jmsTemplate.setObservationRegistry(observationRegistry);
        this.jmsTemplate = jmsTemplate;
        this.producer = trainerWorkloadProducer;



    }

    @PostMapping("/add")
    public ResponseEntity<String> addTraining(@RequestBody TrainingDto trainingDto) {
        trainingService.create(trainingDto);
        Training training = trainingMapper.toEntity(trainingDto);
        TrainerWorkload trainerWorkload = trainingMapper.toTrainerWorkload(training);

        jmsTemplate.convertAndSend(destination, trainerWorkload);

//        producer.sendTo(destination, trainerWorkload);

        return ResponseEntity.ok("Training added and notification sent.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTraining(@PathVariable Long id) {
        Optional<Training> trainingOptional = trainingService.findById(id);

        if (trainingOptional.isPresent()) {
            Training training = trainingOptional.get();

            boolean isDeleted = trainingService.delete(id);

            if (isDeleted) {
                TrainerWorkload trainerWorkload = trainingMapper.toTrainerWorkload(training);

                producer.sendTo("trainerWorkload.queue", trainerWorkload);

                return ResponseEntity.ok("Training deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Training could not be deleted.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Training not found.");
        }
    }
}
