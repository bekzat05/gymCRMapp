package com.aitbekov.gym.service;

import com.aitbekov.gym.dto.CredentialsDto;
import com.aitbekov.gym.model.User;

public interface UserService extends BaseService<User, Long> {

    boolean login(CredentialsDto credentialsDto);

    boolean changePassword(CredentialsDto credentialsDto, String newPassword);
}
