package com.bekzat.boot.gymcrm.mapstruct;

import com.bekzat.boot.gymcrm.dto.TrainingCreateDto;
import com.bekzat.boot.gymcrm.dto.TrainingReadDto;
import com.bekzat.boot.gymcrm.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TrainerMapper.class, TraineeMapper.class} )
public interface TrainingMapper {
    TrainingReadDto toDto(Training training);
    List<TrainingReadDto> toDTOList(List<Training> trainings);

    @Mapping(target = "trainee", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "trainingType", ignore = true)
    Training toEntity(TrainingCreateDto createDto);
}