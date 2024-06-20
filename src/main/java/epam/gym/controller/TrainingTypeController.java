package epam.gym.controller;

import epam.gym.dto.trainingType.TrainingTypeEntityDto;
import epam.gym.entity.TrainingTypeEntity;
import epam.gym.mapper.TrainingTypeEntityMapper;
import epam.gym.service.TrainingTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/training-types")
public class TrainingTypeController {

    private TrainingTypeService trainingTypeService;
    private TrainingTypeEntityMapper trainingTypeEntityMapper = TrainingTypeEntityMapper.trainingTypeMapper;


    TrainingTypeController(TrainingTypeService trainingTypeService){
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingTypeEntityDto>> getAllTrainingTypes() {
        List<TrainingTypeEntity> trainingTypes = trainingTypeService.getAllTrainingTypes();
        List<TrainingTypeEntityDto> trainingTypeDtos = new ArrayList<>();

        for (TrainingTypeEntity trainingType : trainingTypes) {
            trainingTypeDtos.add(trainingTypeEntityMapper.toDto(trainingType));
        }

        return ResponseEntity.ok(trainingTypeDtos);
    }
}
