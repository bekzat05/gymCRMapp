package com.bekzat.gym.mapper;

import com.bekzat.gym.dto.TrainerCreateDto;
import com.bekzat.gym.dto.TrainerProfileReadDto;
import com.bekzat.gym.dto.TrainerReadDto;
import com.bekzat.gym.dto.TrainerRegistrationDto;
import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import com.bekzat.gym.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    @Mapping(target = "specializations", source = "specializations", qualifiedByName = "trainingTypeToType")
    TrainerReadDto toDto(Trainer trainer);

    @Mapping(target = "specialization", source = "specializations", qualifiedByName = "trainingTypeToType")
    TrainerProfileReadDto toProfileDto(Trainer trainer);

    @Named("trainingTypeToType")
    static List<TrainingType.Type> mapSpecializations(List<TrainingType> specializations) {
        if (specializations == null) return null;
        return specializations.stream()
                .map(TrainingType::getName) // Assuming `getName` returns the `Type`
                .collect(Collectors.toList());
    }

    Trainer toEntity(TrainerCreateDto dto);
    Trainer toEntity(TrainerRegistrationDto dto);

    List<TrainerReadDto> toDTOList(List<Trainer> trainers);
    List<TrainerProfileReadDto> toDTOProfileList(List<Training> trainings);
}
