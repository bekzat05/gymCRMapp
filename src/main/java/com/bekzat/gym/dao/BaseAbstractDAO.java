package com.bekzat.gym.dao;

import com.bekzat.gym.model.BaseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public abstract class BaseAbstractDAO<K extends Serializable, T extends BaseEntity> implements BaseDAO<K, T> {

    private final Class<T> clazz;

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<T> findById(K id) {
        return Optional.ofNullable(getCurrentSession().find(clazz, id));
    }

    @Override
    public T save(T entity) {
        if (entity.getId() != null) {
            update(entity);
        } else {
            getCurrentSession().persist(entity);
        }
        return entity;
    }

    @Override
    public void delete(K id) {
        T entity = getCurrentSession().find(clazz, id);
        if (entity != null) {
            getCurrentSession().remove(entity);
        } else {
            throw new IllegalArgumentException("Entity with id " + id + " not found");
        }
    }

    @Override
    public void update(T entity) {
        getCurrentSession().merge(entity);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz).getResultList();
    }
}