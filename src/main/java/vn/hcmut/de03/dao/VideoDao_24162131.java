package vn.hcmut.de03.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import vn.hcmut.de03.entity.Video_24162131;

public interface VideoDao_24162131 {
    // ----- Admin: tất cả video -----
    List<Video_24162131> findAll(int page, int size);

    long countAll();

    // ----- User: video Active = true -----
    List<Video_24162131> findActive(int page, int size);

    long countActive();

    List<Video_24162131> findActiveByCategory(int categoryId, int page, int size);

    long countActiveByCategory(int categoryId);

    // ----- CRUD -----
    Video_24162131 findById(String videoId);

    void insert(Video_24162131 video);

    void update(Video_24162131 video);

    void delete(String videoId);

    void increaseViews(String videoId);

    void decreaseQuantity(String videoId, int qty);

    // ----- Đếm Share / Like theo danh sách videoId -----
    Map<String, Long> countSharesByVideo(Collection<String> videoIds);

    Map<String, Long> countLikesByVideo(Collection<String> videoIds);
}
