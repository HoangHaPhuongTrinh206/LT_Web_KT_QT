package vn.hcmut.de03.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.hcmut.de03.dao.UserDao_24162131;
import vn.hcmut.de03.entity.User_24162131;
import vn.hcmut.de03.util.JpaUtil_24162131;

public class UserDaoImpl_24162131 implements UserDao_24162131 {

    @Override
    public User_24162131 findById(String username) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.find(User_24162131.class, username);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(User_24162131 user) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User_24162131 user) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
