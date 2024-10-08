package com.bekzat.gym.dao;

import com.bekzat.gym.model.Trainer;
import com.bekzat.gym.model.Training;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class TrainerRepository extends BaseAbstractDAO<Long, Trainer>{
    public TrainerRepository() {
        super(Trainer.class);
    }
    public Optional<Trainer> findByUsername(String username) {
        return getCurrentSession().createQuery("SELECT t FROM Trainer t WHERE t.username = :username", Trainer.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }
    public List<Training> findTrainerTrainingsByUsernameAndCriteria(
            String username, Date fromDate, Date toDate, String traineeName) {
        return getCurrentSession().createQuery(
                        "SELECT t FROM Training t JOIN t.trainer tr JOIN t.trainee trn WHERE tr.username = :username " +
                                "AND t.date BETWEEN :fromDate AND :toDate AND trn.firstName LIKE :traineeName", Training.class)
                .setParameter("username", username)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("traineeName", "%" + traineeName + "%")
                .getResultList();
    }
    public List<Trainer> findTrainersNotAssignedToTrainee(String traineeUsername) {
        return getCurrentSession().createQuery(
                        "SELECT t FROM Trainer t WHERE t NOT IN " +
                                "(SELECT tr.trainers FROM Trainee tr WHERE tr.username = :traineeUsername)", Trainer.class)
                .setParameter("traineeUsername", traineeUsername)
                .getResultList();
    }
}
