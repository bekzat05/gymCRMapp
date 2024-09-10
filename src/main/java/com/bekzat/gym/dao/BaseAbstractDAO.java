package com.bekzat.gym.dao;

import com.bekzat.gym.model.BaseEntity;
import com.bekzat.gym.storage.Storage;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public abstract class BaseAbstractDAO<T extends BaseEntity> implements BaseDAO<T>{
    Storage<T> storage;

    public Optional<T> findById(Long id) {
        Optional<T> result = storage.findById(id);
        log.info("findById called with id: {}, result: {}", id, result);
        return result;
    }

    public T save(T entity) {
        T savedEntity = storage.save(entity);
        log.info("Entity saved: {}", savedEntity);
        return savedEntity;
    }
}
