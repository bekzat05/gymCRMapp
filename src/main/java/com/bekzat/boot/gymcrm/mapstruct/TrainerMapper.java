package com.bekzat.boot.gymcrm.mapstruct;

import com.bekzat.boot.gymcrm.dto.TrainerCreateDto;
import com.bekzat.boot.gymcrm.dto.TrainerProfileReadDto;
import com.bekzat.boot.gymcrm.dto.TrainerReadDto;
import com.bekzat.boot.gymcrm.dto.TrainerRegistrationDto;
import com.bekzat.boot.gymcrm.model.Trainer;
import com.bekzat.boot.gymcrm.model.Training;
import com.bekzat.boot.gymcrm.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrainerMapper {


    @Mapping(target = "specialization", source = "specializations", qualifiedByName = "trainingTypeToType")
    TrainerReadDto toDto(Trainer trainer);

    @Mapping(target = "specialization", source = "specializations", qualifiedByName = "trainingTypeToType")
    TrainerProfileReadDto toProfileDto(Trainer trainer);


    @Named("trainingTypeToType")
    static List<TrainingType.Type> mapSpecializations(List<TrainingType> specializations) {
        if (specializations == null) return null;
        return specializations.stream()
                .map(TrainingType::getName)
                .collect(Collectors.toList());
    }

    Trainer toEntity(TrainerCreateDto dto);
    Trainer toEntity(TrainerRegistrationDto dto);

    List<TrainerReadDto> toDTOList(List<Trainer> trainers);
    List<TrainerProfileReadDto> toDTOProfileList(List<Training> trainings);
}
