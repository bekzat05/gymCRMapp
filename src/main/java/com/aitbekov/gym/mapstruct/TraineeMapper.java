package com.aitbekov.gym.mapstruct;

import com.aitbekov.gym.dto.TraineeCreateAndUpdateDto;
import com.aitbekov.gym.dto.TraineeReadDto;
import com.aitbekov.gym.dto.TraineeProfileReadDto;
import com.aitbekov.gym.dto.TraineeRegistrationDto;
import com.aitbekov.gym.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface    TraineeMapper {

    @Mapping(target = "username", expression = "java(trainee.getUsername())")
    TraineeReadDto toDto(Trainee trainee);


    TraineeCreateAndUpdateDto toCreateAndUpdateDto(TraineeRegistrationDto registrationDto);

    @Mapping(target = "trainers", expression = "java(new java.util.ArrayList<>())")
    TraineeProfileReadDto toProfileReadDto(Trainee trainee);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "isActive", source = "isActive")
    Trainee toEntity(TraineeCreateAndUpdateDto dto);


    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    Trainee toEntity(TraineeRegistrationDto dto);


}
