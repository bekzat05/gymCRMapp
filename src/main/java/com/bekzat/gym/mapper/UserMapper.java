package com.bekzat.gym.mapper;

import com.bekzat.gym.dto.UserReadDto;
import com.bekzat.gym.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReadDto toDto(User user);
}
