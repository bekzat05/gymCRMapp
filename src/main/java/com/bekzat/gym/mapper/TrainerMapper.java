package com.bekzat.gym.mapper;

import com.bekzat.gym.dto.TrainerCreateDto;
import com.bekzat.gym.dto.TrainerReadDto;
import com.bekzat.gym.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    @Mapping(target = "name", expression = "java(trainer.getFirstName() + ' ' + trainer.getLastName())")
    TrainerReadDto toDto(Trainer trainer);

    Trainer toEntity(TrainerCreateDto dto);

    List<TrainerReadDto> toDTOList(List<Trainer> trainers);
}
