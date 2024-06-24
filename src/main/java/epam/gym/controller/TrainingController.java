package epam.gym.controller;

import epam.gym.dto.training.TrainingDto;
import epam.gym.mapper.TrainingMapper;
import epam.gym.service.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private TrainingService trainingService;
    private TrainingMapper trainingMapper = TrainingMapper.trainingMapper;

    TrainingController(TrainingService trainingService){
        this.trainingService = trainingService;

    }

    @PostMapping("/add")
    public ResponseEntity<String> addTraining(@RequestBody TrainingDto trainingDto) {
        trainingService.create(trainingMapper.toEntity(trainingDto));
        return ResponseEntity.ok("200 OK");
    }

}
