package com.bekzat.gym.dao;

import com.bekzat.gym.model.TrainingType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrainingTypeRepository extends BaseAbstractDAO<Long, TrainingType> {
    public TrainingTypeRepository() {
        super(TrainingType.class);
    }

    public Optional<TrainingType> findByName(TrainingType.Type name) {
        return getCurrentSession().createQuery("SELECT t FROM TrainingType t WHERE t.name = :name", TrainingType.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    public List<TrainingType> findByByNames(List<TrainingType.Type> types) {
        return getCurrentSession().createQuery("SELECT t FROM TrainingType t WHERE t.name IN :types", TrainingType.class)
                .setParameter("types", types)
                .getResultList();
    }
}
