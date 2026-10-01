package vn.hcmut.de03.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.hcmut.de03.dao.VideoDao_24162131;
import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.entity.Video_24162131;
import vn.hcmut.de03.util.JpaUtil_24162131;

public class VideoDaoImpl_24162131 implements VideoDao_24162131 {

    // ================= Admin =================
    @Override
    public List<Video_24162131> findAll(int page, int size) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery("select v from Video v left join fetch v.category order by v.videoId desc",
                    Video_24162131.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countAll() {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery("select count(v) from Video v", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    // ================= User =================
    @Override
    public List<Video_24162131> findActive(int page, int size) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery(
                    "select v from Video v left join fetch v.category where v.active = true order by v.videoId",
                    Video_24162131.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countActive() {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery("select count(v) from Video v where v.active = true", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video_24162131> findActiveByCategory(int categoryId, int page, int size) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery(
                    "select v from Video v join fetch v.category c "
                            + "where v.active = true and c.categoryId = :cid order by v.videoId",
                    Video_24162131.class)
                    .setParameter("cid", categoryId)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countActiveByCategory(int categoryId) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            return em.createQuery(
                    "select count(v) from Video v where v.active = true and v.category.categoryId = :cid",
                    Long.class)
                    .setParameter("cid", categoryId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // ================= CRUD =================
    @Override
    public Video_24162131 findById(String videoId) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            List<Video_24162131> list = em.createQuery(
                    "select v from Video v left join fetch v.category where v.videoId = :id",
                    Video_24162131.class)
                    .setParameter("id", videoId)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Video_24162131 video) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (video.getCategory() != null && video.getCategory().getCategoryId() != null) {
                video.setCategory(em.getReference(Category_24162131.class, video.getCategory().getCategoryId()));
            }
            em.persist(video);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Video_24162131 video) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Video_24162131 db = em.find(Video_24162131.class, video.getVideoId());
            if (db != null) {
                db.setTitle(video.getTitle());
                db.setViews(video.getViews());
                db.setDescription(video.getDescription());
                db.setActive(video.getActive());
                if (video.getPoster() != null) {          // null = giữ poster cũ
                    db.setPoster(video.getPoster());
                }
                if (video.getCategory() != null && video.getCategory().getCategoryId() != null) {
                    db.setCategory(em.getReference(Category_24162131.class, video.getCategory().getCategoryId()));
                }
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
    public void delete(String videoId) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Video_24162131 v = em.find(Video_24162131.class, videoId);
            if (v != null) {
                em.remove(v);      // cascade REMOVE -> xóa Shares + Favorites trước
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
    public void increaseViews(String videoId) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("update Video v set v.views = coalesce(v.views, 0) + 1 where v.videoId = :id")
                    .setParameter("id", videoId)
                    .executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void decreaseQuantity(String videoId, int qty) {
        EntityManager em = JpaUtil_24162131.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("update Video v set v.quantity = v.quantity - :qty where v.videoId = :id and v.quantity >= :qty")
                    .setParameter("qty", qty)
                    .setParameter("id", videoId)
                    .executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= Share / Like count =================
    @Override
    public Map<String, Long> countSharesByVideo(Collection<String> videoIds) {
        return countBy("select s.video.videoId, count(s) from Share s "
                + "where s.video.videoId in :ids group by s.video.videoId", videoIds);
    }

    @Override
    public Map<String, Long> countLikesByVideo(Collection<String> videoIds) {
        return countBy("select f.video.videoId, count(f) from Favorite f "
                + "where f.video.videoId in :ids group by f.video.videoId", videoIds);
    }

    private Map<String, Long> countBy(String jpql, Collection<String> videoIds) {
        Map<String, Long> result = new HashMap<>();
        if (videoIds == null || videoIds.isEmpty()) {
            return result;
        }
        EntityManager em = JpaUtil_24162131.getEntityManager();
        try {
            List<Object[]> rows = em.createQuery(jpql, Object[].class)
                    .setParameter("ids", videoIds)
                    .getResultList();
            for (Object[] r : rows) {
                result.put((String) r[0], (Long) r[1]);
            }
            return result;
        } finally {
            em.close();
        }
    }
}
