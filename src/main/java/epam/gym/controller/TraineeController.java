package epam.gym.controller;

import epam.gym.dto.trainee.TraineeStatusUpdateDto;
import epam.gym.dto.trainee.TraineeUpdateDto;
import epam.gym.dto.trainer.TrainerDto;
import epam.gym.dto.trainer.TrainerListUpdateDto;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.mapper.TraineeMapper;
import epam.gym.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/trainees")
public class TraineeController {

    private static final Logger log = LoggerFactory.getLogger(TraineeController.class);
    private TraineeService traineeService;
    private final TraineeMapper traineeMapper = TraineeMapper.traineeMapper;

    TraineeController(TraineeService traineeService){
        this.traineeService = traineeService;

    }

    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> getTraineeProfile(@PathVariable("username") String username) {
        log.info("Looking for a new trainee with  username: {}", username);
        Optional<Trainee> traineeOpt = traineeService.findByUsername(username);

        if (!traineeOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Trainee trainee = traineeOpt.get();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("firstName", trainee.getFirstName());
        response.put("lastName", trainee.getLastName());
        response.put("dateOfBirth", trainee.getDateOfBirth());
        response.put("address", trainee.getAddress());
        response.put("isActive", trainee.isEnabled());

        List<Map<String, Object>> trainersList = new ArrayList<>();
        for (Trainer trainer : trainee.getTrainers()) {
            Map<String, Object> trainerMap = new LinkedHashMap<>();
            trainerMap.put("username", trainer.getUsername());
            trainerMap.put("firstName", trainer.getFirstName());
            trainerMap.put("lastName", trainer.getLastName());
            trainerMap.put("specialization", trainer.getSpecialization());
            trainersList.add(trainerMap);
        }
        response.put("trainers", trainersList);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateTraineeProfile(@PathVariable("id") long id, @RequestBody TraineeUpdateDto traineeUpdateDto) {
        Trainee trainee = traineeMapper.toEntity(traineeUpdateDto);
        trainee.setId(id);
        Trainee updatedTrainee = traineeService.update(trainee.getId(), trainee, traineeUpdateDto.getUsername(), traineeUpdateDto.getPassword());
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("firstName", updatedTrainee.getFirstName());
        response.put("lastName", updatedTrainee.getLastName());
        response.put("username", updatedTrainee.getUsername());
        response.put("dateOfBirth", updatedTrainee.getDateOfBirth());
        response.put("address", updatedTrainee.getAddress());
        response.put("isActive", updatedTrainee.isEnabled());

        List<Map<String, Object>> trainersList = new ArrayList<>();
        if (updatedTrainee.getTrainers() != null) {
            for (Trainer trainer : updatedTrainee.getTrainers()) {
                Map<String, Object> trainerMap = new LinkedHashMap<>();
                trainerMap.put("username", trainer.getUsername());
                trainerMap.put("firstName", trainer.getFirstName());
                trainerMap.put("lastName", trainer.getLastName());
                trainerMap.put("specialization", trainer.getSpecialization());
                trainersList.add(trainerMap);
            }
        }
        response.put("trainers", trainersList);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTraineeProfileByUsername(@RequestParam("username") String username) {
        Optional<Trainee> traineeOptional = traineeService.findByUsername(username);
        if (traineeOptional.isPresent()) {
            Trainee trainee = traineeOptional.get();
            traineeService.deleteTraineeByUsername(trainee.getUsername(), trainee.getPassword());
            return ResponseEntity.ok("Trainee deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/updateStatus")
    public ResponseEntity<String> updateTraineeStatus(@RequestBody TraineeStatusUpdateDto request) {
        try {
            traineeService.setActiveStatus(request.getUsername(), request.isActive());
            return ResponseEntity.ok("Trainee status updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{username}/not-assigned-trainers")
    public ResponseEntity<List<TrainerDto>> getNotAssignedTrainers(@PathVariable("username") String traineeUsername) {
        List<Trainer> trainers = traineeService.getNotAssignedTrainers(traineeUsername);
        List<TrainerDto> trainerDtos = new ArrayList<>();

        for (Trainer trainer : trainers) {
            trainerDtos.add(traineeMapper.toDto(trainer));
        }

        return ResponseEntity.ok(trainerDtos);
    }

    @PutMapping("/update-trainers")
    public ResponseEntity<List<TrainerDto>> updateTraineeTrainers(@RequestBody TrainerListUpdateDto request) {
        List<TrainerDto> trainers = traineeService.updateTraineeTrainers(request.getTraineeUsername(), request.getTrainerUsernames());
        return ResponseEntity.ok(trainers);
    }

}
