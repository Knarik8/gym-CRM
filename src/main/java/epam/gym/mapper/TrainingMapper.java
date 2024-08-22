package epam.gym.mapper;

import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.TrainerWorkload;
import epam.gym.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainingMapper {

    TrainingMapper trainingMapper = Mappers.getMapper(TrainingMapper.class);

    @Mapping(source = "trainingTypeId", target = "trainingType.id")
    @Mapping(source = "traineeId", target = "trainee.id")
    @Mapping(source = "trainerId", target = "trainer.id")
    Training toEntity(TrainingDto dto);


    @Mapping(source = "trainer.username", target = "username")
    @Mapping(source = "trainer.firstName", target = "firstName")
    @Mapping(source = "trainer.lastName", target = "lastName")
    @Mapping(source = "training.trainingDuration", target = "trainingDuration")
    TrainerWorkload toTrainerWorkload(Training training);

    @Mapping(source = "trainingType.id", target = "trainingTypeId")
    @Mapping(source = "trainee.id", target = "traineeId")
    @Mapping(source = "trainer.id", target = "trainerId")
    TrainingDto toDto(Training entity);

}
