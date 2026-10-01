package vn.hcmut.de03.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import vn.hcmut.de03.dao.CategoryDao_24162131;
import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.util.JpaUtil_24162131;

public class CategoryDaoImpl_24162131 implements CategoryDao_24162131 {

    @Override
    public List<Category_24162131> findAll() {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery("select c from Category c order by c.categoryId", Category_24162131.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category_24162131> findActive() {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery("select c from Category c where c.status = true order by c.categoryId",
                    Category_24162131.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category_24162131 findById(int categoryId) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.find(Category_24162131.class, categoryId);
        } finally {
            em.close();
        }
    }
}
