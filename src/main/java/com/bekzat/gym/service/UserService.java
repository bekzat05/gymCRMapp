package com.bekzat.gym.service;

import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.model.User;

public interface UserService extends BaseService<User, Long> {
    boolean login(CredentialsDto credentialsDto);

    boolean changePassword(CredentialsDto credentialsDto, String newPassword);
}
