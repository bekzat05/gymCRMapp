package com.bekzat.gym.dao;

import com.bekzat.gym.model.BaseEntity;
import com.bekzat.gym.storage.Storage;

import java.util.Optional;

public abstract class BaseAbstractDAO<T extends BaseEntity> implements BaseDAO<T>{
    Storage<T> storage;

    public Optional<T> findById(Long id) {
        return storage.findById(id);
    }

    public T save(T entity) {
        return storage.save(entity);
    }
}
