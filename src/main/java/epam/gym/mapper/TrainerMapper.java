package epam.gym.mapper;

import epam.gym.dto.trainer.TrainerRegistrationDto;
import epam.gym.dto.trainer.TrainerUpdateDto;
import epam.gym.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TrainerMapper {

    TrainerMapper trainerMapper = Mappers.getMapper(TrainerMapper.class);

    Trainer toEntity(TrainerRegistrationDto dto);
    Trainer toEntity(TrainerUpdateDto dto);



}
