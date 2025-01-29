package com.aitbekov.gym.mapstruct;

import com.aitbekov.gym.dto.TrainingCreateDto;
import com.aitbekov.gym.dto.TrainingReadDto;
import com.aitbekov.gym.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {TrainerMapper.class, TraineeMapper.class} )
public interface TrainingMapper {
    @Mapping(target = "type", source = "trainingType.name")
    TrainingReadDto toDto(Training training);

    @Mapping(target = "type", source = "trainingType.name")
    Set<TrainingReadDto> toDtoList(Set<Training> trainings);

    @Mapping(target = "trainee", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "trainingType", ignore = true)
    Training toEntity(TrainingCreateDto createDto);
}
