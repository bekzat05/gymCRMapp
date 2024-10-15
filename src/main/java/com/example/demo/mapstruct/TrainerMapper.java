package com.example.demo.mapstruct;

import com.example.demo.dto.TrainerCreateDto;
import com.example.demo.dto.TrainerProfileReadDto;
import com.example.demo.dto.TrainerReadDto;
import com.example.demo.dto.TrainerRegistrationDto;
import com.example.demo.model.Trainer;
import com.example.demo.model.Training;
import com.example.demo.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
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
