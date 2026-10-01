package vn.hcmut.de03.service;

import java.util.List;
import java.util.Map;

import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.entity.Video_24162131;
import vn.hcmut.de03.model.CategoryBlock_24162131;
import vn.hcmut.de03.model.PageResult_24162131;

public interface VideoService_24162131 {
    // Admin (Câu 2 - CRUD, 6 video/trang)
    PageResult_24162131<Video_24162131> adminPage(int page, int size);

    Video_24162131 findById(String videoId);

    void create(Video_24162131 video);

    void update(Video_24162131 video);

    void delete(String videoId);

    long countAll();

    // User
    PageResult_24162131<Video_24162131> activePage(int page, int size);

    /** Câu 3: chi tiết 1 video (tăng View +1, kèm số Share / Like). */
    Video_24162131 viewDetail(String videoId);

    /** Câu 4 + Câu 5: mỗi category 1 khối, phân trang riêng, kèm tổng số video. */
    List<CategoryBlock_24162131> buildHomeBlocks(List<Category_24162131> categories,
            Map<Integer, Integer> requestedPages, int size);
}
