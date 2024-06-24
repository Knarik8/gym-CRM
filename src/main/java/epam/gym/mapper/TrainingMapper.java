package epam.gym.mapper;

import epam.gym.dto.training.TrainingDto;
import epam.gym.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainingMapper {

    TrainingMapper trainingMapper = Mappers.getMapper(TrainingMapper.class);

    Training toEntity(TrainingDto dto);

}
