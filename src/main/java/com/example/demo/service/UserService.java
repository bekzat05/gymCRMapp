package com.example.demo.service;

import com.example.demo.dto.CredentialsDto;
import com.example.demo.model.User;

public interface UserService extends BaseService<User, Long>{

    boolean login(CredentialsDto credentialsDto);

    boolean changePassword(CredentialsDto credentialsDto, String newPassword);
}
