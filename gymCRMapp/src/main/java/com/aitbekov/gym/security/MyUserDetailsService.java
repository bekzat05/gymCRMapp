package com.aitbekov.gym.security;

import com.aitbekov.gym.exceptions.AuthenticationException;
import com.aitbekov.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;
    @Value("${auth.message.blocked}")
    private String blockedMessage;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(loginAttemptService.isBlocked()) {
            throw new AuthenticationException(blockedMessage);
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
