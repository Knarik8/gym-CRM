package epam.gym.service.hibernateImpl;

import epam.gym.dao.TrainingDao;
import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.ActionType;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.TrainerWorkload;
import epam.gym.entity.Training;
import epam.gym.mapper.TrainingMapper;
import epam.gym.producer.TrainerWorkloadProducer;
import epam.gym.service.TraineeService;
import epam.gym.service.TrainerService;
import epam.gym.service.TrainingService;
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

    private TrainingDao trainingDao;
    private TrainingMapper trainingMapper = TrainingMapper.trainingMapper;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private final RestTemplate restTemplate;





    public TrainingServiceImpl(TrainingDao trainingDao, RestTemplate restTemplate, @Lazy TrainerService trainerService, TraineeService traineeService){
        this.trainingDao = trainingDao;
        this.restTemplate = restTemplate;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }


    @Override
    public Training create(@NonNull TrainingDto trainingDto) {
        Optional<Trainee> trainee = traineeService.findById(trainingDto.getTraineeId());
        Optional<Trainer> trainer = trainerService.findById(trainingDto.getTrainerId());
        Training training = trainingMapper.toEntity(trainingDto);
        training.setTrainee(trainee.get());
        training.setTrainer(trainer.get());
        trainingDao.create(training);
        logger.info("Training created with ID: {}", training.getId());
        TrainerWorkload trainerWorkload = trainingMapper.toTrainerWorkload(training);
        trainerWorkload.setUsername(training.getTrainer().getUsername());
        trainerWorkload.setActionType(trainerWorkload.getActionType());
//        producer.sendTo(destination, trainerWorkload);
//        notifyTrainingUpdate(training, ActionType.ADD);
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

    public boolean delete(Long id) {
        Optional<Training> trainingOptional = trainingDao.findById(id);
        if (trainingOptional.isPresent()) {
            trainingDao.delete(id);
            notifyTrainingUpdate(trainingOptional.get(), ActionType.DELETE);
            return true;
        } else {
            return false;
        }
    }

}
