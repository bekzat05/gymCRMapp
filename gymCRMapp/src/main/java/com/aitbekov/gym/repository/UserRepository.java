package com.aitbekov.gym.repository;

import com.aitbekov.gym.dto.CredentialsDto;
import com.aitbekov.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.username = :#{#credentials.username} AND u.password = :#{#credentials.password}")
    Optional<User> checkCredentials(@Param("credentials") CredentialsDto credentials);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :#{#dto.username} AND u.password = :#{#dto.password}")
    boolean login(@Param("dto") CredentialsDto dto);

    Optional<User> findByUsername(String username);

    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
    @Modifying
    void updateFailedAttempt(int failedAttempt, String username);
}
