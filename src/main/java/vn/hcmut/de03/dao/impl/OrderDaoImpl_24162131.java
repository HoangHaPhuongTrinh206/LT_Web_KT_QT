package vn.hcmut.de03.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.hcmut.de03.dao.OrderDao_24162131;
import vn.hcmut.de03.entity.Order_24162131;
import vn.hcmut.de03.util.JpaUtil_24162131;

public class OrderDaoImpl_24162131 implements OrderDao_24162131 {
    @Override
    public void insert(Order_24162131 order) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(order);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24162131> findByUser(String username) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery("select o from Order_24162131 o where o.user.username = :username order by o.orderDate desc", Order_24162131.class)
                     .setParameter("username", username)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24162131> findByUserAndStatus(String username, Integer status) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery("select o from Order_24162131 o where o.user.username = :username and o.status = :status order by o.orderDate desc", Order_24162131.class)
                     .setParameter("username", username)
                     .setParameter("status", status)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void updateStatus(Integer orderId, Integer status) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order_24162131 db = em.find(Order_24162131.class, orderId);
            if (db != null) {
                db.setStatus(status);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Order_24162131 findById(Integer orderId) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.find(Order_24162131.class, orderId);
        } finally {
            em.close();
        }
    }
}
