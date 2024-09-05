package com.bekzat.gym.service;

public interface BaseService<T, ID> {
    T findById(ID id);
    T save(T t);
}
