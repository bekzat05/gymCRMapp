package com.aitbekov.gym.mapstruct;

import com.aitbekov.gym.dto.UserReadDto;
import com.aitbekov.gym.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserReadDto toDto(User user);

    List<UserReadDto> toDtoList(List<User> users);
}
