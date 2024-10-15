package com.bekzat.gym.dao;

import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends BaseAbstractDAO<Long, User> {
    public UserRepository() {
        super(User.class);
    }

    public boolean existsByUsername(String username) {
        Long count = getCurrentSession().createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }
    public boolean checkCredentials(String username, String password) {
        Long count = getCurrentSession().createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.password = :password", Long.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        return count > 0;
    }

    public boolean login(CredentialsDto dto) {
        Long count = getCurrentSession().createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.password = :password", Long.class)
                .setParameter("username", dto.username())
                .setParameter("password", dto.password())
                .getSingleResult();
        return count > 0;
    }

    public Optional<User> findByUsername(String username) {
        return getCurrentSession().createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }
}
