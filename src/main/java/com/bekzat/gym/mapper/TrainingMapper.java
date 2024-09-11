package com.bekzat.gym.mapper;

import com.bekzat.gym.dto.TrainingReadDto;
import com.bekzat.gym.model.Training;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TrainerMapper.class, TraineeMapper.class} )
public interface TrainingMapper {
    TrainingReadDto toDto(Training training);
    List<TrainingReadDto> toDTOList(List<Training> trainings);
}
