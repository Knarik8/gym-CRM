package epam.gym.mapper;

import epam.gym.dto.trainee.TraineeRegistrationDto;
import epam.gym.dto.trainee.TraineeUpdateDto;
import epam.gym.dto.trainer.TrainerDto;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TraineeMapper {

    TraineeMapper traineeMapper = Mappers.getMapper(TraineeMapper.class);

    Trainee toEntity(TraineeRegistrationDto dto);

    Trainee toEntity(TraineeUpdateDto dto);

    TrainerDto toDto(Trainer trainer);


}
