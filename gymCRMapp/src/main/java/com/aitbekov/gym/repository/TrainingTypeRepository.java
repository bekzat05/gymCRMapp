package com.aitbekov.gym.repository;

import com.aitbekov.gym.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
    Optional<TrainingType> findByName(TrainingType.Type name);

    @Query("SELECT t FROM TrainingType t WHERE t.name IN (:types)")
    Set<TrainingType> findByNames(@Param("types") List<TrainingType.Type> types);
}
