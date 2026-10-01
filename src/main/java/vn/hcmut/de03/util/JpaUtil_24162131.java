package vn.hcmut.de03.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/** Tạo EntityManager từ persistence-unit "De03PU" (META-INF/persistence.xml). */
public class JpaUtil_24162131 {
    private static EntityManagerFactory emf;

    private JpaUtil_24162131() {
    }

    public static synchronized EntityManagerFactory getFactory() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory("De03PU");
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getFactory().createEntityManager();
    }

    public static synchronized void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
