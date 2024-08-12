package epam.gym.mapper;

import epam.gym.dto.trainingType.TrainingTypeEntityDto;
import epam.gym.entity.TrainingTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainingTypeEntityMapper {

    TrainingTypeEntityMapper trainingTypeMapper = Mappers.getMapper(TrainingTypeEntityMapper.class);

    TrainingTypeEntityDto toDto(TrainingTypeEntity trainingTypeEntity);


}
