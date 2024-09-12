package epam.gym.service.hibernateImpl;

import epam.gym.dao.TrainingDao;
import epam.gym.dao.TrainingTypeDao;
import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.ActionType;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.TrainerWorkload;
import epam.gym.entity.Training;
import epam.gym.entity.TrainingTypeEntity;
import epam.gym.exception.TrainingDeletionException;
import epam.gym.exception.TrainingNotFoundException;
import epam.gym.mapper.TrainingMapper;
import epam.gym.service.TraineeService;
import epam.gym.service.TrainerService;
import epam.gym.service.TrainingService;
import io.micrometer.observation.ObservationRegistry;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TrainingTypeDao trainingTypeDao;

    @Value("${activemq.destination}")
    private String destination;

    private TrainingDao trainingDao;
    private TrainingMapper trainingMapper = TrainingMapper.trainingMapper;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private final RestTemplate restTemplate;
    private final JmsTemplate jmsTemplate;






    public TrainingServiceImpl(TrainingDao trainingDao, RestTemplate restTemplate, @Lazy TrainerService trainerService,
                               TraineeService traineeService, JmsTemplate jmsTemplate, ObservationRegistry observationRegistry, TrainingTypeDao trainingTypeDao){
        this.trainingDao = trainingDao;
        this.restTemplate = restTemplate;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        jmsTemplate.setObservationRegistry(observationRegistry);
        this.jmsTemplate = jmsTemplate;
        this.trainingTypeDao = trainingTypeDao;
    }


    @Override
    public Training create(@NonNull TrainingDto trainingDto) {
        Optional<Trainee> trainee = traineeService.findById(trainingDto.getTraineeId());
        Optional<Trainer> trainer = trainerService.findById(trainingDto.getTrainerId());
        Training training = trainingMapper.toEntity(trainingDto);
        training.setTrainee(trainee.get());
        training.setTrainer(trainer.get());
        TrainingTypeEntity trainingType = trainingTypeDao.findById(trainingDto.getTrainingTypeId());
        training.setTrainingType(trainingType);
        trainingDao.create(training);
        logger.info("Training created with ID: {}", training.getId());
        TrainerWorkload trainerWorkload = trainingMapper.toTrainerWorkload(training);
        trainerWorkload.setUsername(training.getTrainer().getUsername());
        trainerWorkload.setActionType(trainerWorkload.getActionType());

        convertAndSendToConsumer(training, destination, ActionType.ADD);

        return training;    }


    @Override
    public Optional<Training> findById(Long id) {
        Optional<Training> training = trainingDao.findById(id);
        if (training != null) {
            logger.info("Found training with ID: {}", id);
            return training;
        } else {
            logger.warn("Training with ID: {} not found", id);
        }
        return Optional.empty();
    }

    public void notifyTrainingUpdate(Training training, ActionType actionType) {
        String url = "http://localhost:8081/reports/trainers/update";
        TrainerWorkload trainerWorkload = trainingMapper.toTrainerWorkload(training);
        trainerWorkload.setUsername(training.getTrainer().getUsername());
        trainerWorkload.setActionType(actionType);
        restTemplate.postForEntity(url, trainerWorkload, String.class);
    }

    public void delete(Long id) {
        Optional<Training> trainingOptional = trainingDao.findById(id);

        if (trainingOptional.isPresent()) {
            boolean isDeleted = trainingDao.delete(id);
            if (!isDeleted) {
                throw new TrainingDeletionException();
            }
            convertAndSendToConsumer(trainingOptional.get(), destination, ActionType.DELETE);
            logger.info("Training deleted with ID: {}", id);
        } else {
            throw new TrainingNotFoundException(id);
        }
    }

    public void convertAndSendToConsumer(Training training, String destination, ActionType actionType){
        TrainerWorkload trainerWorkload = trainingMapper.toTrainerWorkload(training);
        trainerWorkload.setActionType(actionType);

        jmsTemplate.convertAndSend(destination, trainerWorkload);
    }

}
