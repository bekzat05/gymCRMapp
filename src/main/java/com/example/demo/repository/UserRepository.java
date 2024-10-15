package com.example.demo.repository;

import com.example.demo.dto.CredentialsDto;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.username = :#{#dto.username} AND u.password = :#{#dto.password}")
    Optional<User> checkCredentials(@Param("dto") CredentialsDto dto);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :#{#dto.username} AND u.password = :#{#dto.password}")
    boolean login(@Param("dto") CredentialsDto dto);

    Optional<User> findByUsername(String username);
}
