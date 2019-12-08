/*
package db.services;

import db.entities.PersonalInfo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PersonalInfoService {
    private EntityManager em = Persistence.createEntityManagerFactory("USER").createEntityManager();

    public PersonalInfo add(PersonalInfo personalInfo){
        em.getTransaction().begin();
        PersonalInfo personalInfoDb = em.merge(personalInfo);
        em.getTransaction().commit();
        return personalInfoDb;
    }

}
*/
