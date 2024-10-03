package com.bekzat.boot.gymcrm.service;

import com.bekzat.boot.gymcrm.dto.CredentialsDto;
import com.bekzat.boot.gymcrm.model.User;

public interface UserService extends BaseService<User, Long>{

    boolean login(CredentialsDto credentialsDto);

    boolean changePassword(CredentialsDto credentialsDto, String newPassword);
}
