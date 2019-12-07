package db.services;

import db.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class UserService {
    private EntityManager em = Persistence.createEntityManagerFactory("USER").createEntityManager();

    public User add(User user){
        em.getTransaction().begin();
        User userFromDb = em.merge(user);
        em.getTransaction().commit();
        return userFromDb;
    }
}
