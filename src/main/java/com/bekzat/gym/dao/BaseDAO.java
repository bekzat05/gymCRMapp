package com.bekzat.gym.dao;

import com.bekzat.gym.model.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseDAO<K extends Serializable, T extends BaseEntity> {

    T save(T entity);
    void delete(K id);
    void update(T entity);
    Optional<T> findById(K id);
    List<T> findAll();

}
