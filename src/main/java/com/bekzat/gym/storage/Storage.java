package com.bekzat.gym.storage;

import com.bekzat.gym.model.BaseEntity;

import java.util.Collection;
import java.util.Optional;

public interface Storage<T extends BaseEntity> {
    Optional<T> findById(Long id);

    Collection<T> findAll();

    T save(T entity);

    void delete(Long id);
}
