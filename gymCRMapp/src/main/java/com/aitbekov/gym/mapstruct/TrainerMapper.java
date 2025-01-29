package com.aitbekov.gym.mapstruct;

import com.aitbekov.gym.dto.TrainerProfileReadDto;
import com.aitbekov.gym.dto.TrainerCreateDto;
import com.aitbekov.gym.dto.TrainerReadDto;
import com.aitbekov.gym.dto.TrainerRegistrationDto;
import com.aitbekov.gym.model.Trainer;
import com.aitbekov.gym.model.Training;
import com.aitbekov.gym.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "trainingTypeToType")
    TrainerReadDto toDto(Trainer trainer);

    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "trainingTypeToType")
    TrainerProfileReadDto toProfileReadDto(Trainer trainer);

    @Named("trainingTypeToType")
    static List<TrainingType.Type> mapSpecializations(Set<TrainingType> specializations) {
        if (specializations == null) return null;
        return specializations.stream()
                .map(TrainingType::getName)
                .collect(Collectors.toList());
    }

    Trainer toEntity(TrainerCreateDto dto);

    Trainer toEntity(TrainerRegistrationDto dto);

    Set<TrainerReadDto> toDtoList(Set<Trainer> trainers);
    Set<TrainerProfileReadDto> toDtoProfileList(Set<Training> trainings);
}
