package db.services;

import db.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class UserService {
    private static final EntityManager em = Persistence.createEntityManagerFactory("USER").createEntityManager();

    public User add(User user){
        em.getTransaction().begin();
        user = em.merge(user);
        em.getTransaction().commit();
        return user;
    }
}
