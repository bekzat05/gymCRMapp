package com.bekzat.gym.dao;

import com.bekzat.gym.model.BaseEntity;

import java.util.Optional;

public interface BaseDAO<T extends BaseEntity> {
    Optional<T> findById(Long id);

    T save(T entity);
}
