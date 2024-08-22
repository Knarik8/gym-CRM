package epam.gym.controller;

import epam.gym.dto.training.TrainingDto;
import epam.gym.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private TrainingService trainingService;

    TrainingController(TrainingService trainingService){
        this.trainingService = trainingService;

    }

    @PostMapping("/add")
    public ResponseEntity<String> addTraining(@RequestBody TrainingDto trainingDto) {
        trainingService.create(trainingDto);
        return ResponseEntity.ok("Training added and notification sent.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTraining(@PathVariable Long id) {
        boolean isDeleted = trainingService.delete(id);
        if (isDeleted) {
            return ResponseEntity.ok("Training deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training not found.");
        }
    }

}
