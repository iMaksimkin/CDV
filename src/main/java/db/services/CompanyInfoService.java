package db.services;

import db.entities.CompanyInfo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class CompanyInfoService {
    private EntityManager em = Persistence.createEntityManagerFactory("USER").createEntityManager();

    public CompanyInfo add(CompanyInfo companyInfoService){
        em.getTransaction().begin();
        CompanyInfo companyInfoFromDb = em.merge(companyInfoService);
        em.getTransaction().commit();
        return companyInfoFromDb;
    }
}
