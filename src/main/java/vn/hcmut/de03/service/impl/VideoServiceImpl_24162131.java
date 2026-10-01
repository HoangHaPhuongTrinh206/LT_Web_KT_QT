package vn.hcmut.de03.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.hcmut.de03.dao.VideoDao_24162131;
import vn.hcmut.de03.dao.impl.VideoDaoImpl_24162131;
import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.entity.Video_24162131;
import vn.hcmut.de03.model.CategoryBlock_24162131;
import vn.hcmut.de03.model.PageResult_24162131;
import vn.hcmut.de03.service.VideoService_24162131;

public class VideoServiceImpl_24162131 implements VideoService_24162131 {
    private final VideoDao_24162131 videoDao = new VideoDaoImpl_24162131();

    // ---------------- Admin ----------------
    @Override
    public PageResult_24162131<Video_24162131> adminPage(int page, int size) {
        long total = videoDao.countAll();
        int p = PageResult_24162131.clampPage(page, total, size);
        List<Video_24162131> items = videoDao.findAll(p, size);
        fillCounts(items);
        return new PageResult_24162131<>(items, p, size, total);
    }

    @Override
    public Video_24162131 findById(String videoId) {
        return videoDao.findById(videoId);
    }

    @Override
    public void create(Video_24162131 video) {
        if (video.getVideoId() == null || video.getVideoId().isBlank()) {
            video.setVideoId("VID" + System.currentTimeMillis());   // VideoId nvarchar -> tự sinh mã nếu để trống
        } else if (videoDao.findById(video.getVideoId()) != null) {
            throw new IllegalStateException("Mã video \"" + video.getVideoId() + "\" đã tồn tại.");
        }
        if (video.getViews() == null) {
            video.setViews(0);
        }
        videoDao.insert(video);
    }

    @Override
    public void update(Video_24162131 video) {
        videoDao.update(video);
    }

    @Override
    public void delete(String videoId) {
        videoDao.delete(videoId);
    }

    @Override
    public long countAll() {
        return videoDao.countAll();
    }

    // ---------------- User ----------------
    @Override
    public PageResult_24162131<Video_24162131> activePage(int page, int size) {
        long total = videoDao.countActive();
        int p = PageResult_24162131.clampPage(page, total, size);
        List<Video_24162131> items = videoDao.findActive(p, size);
        fillCounts(items);
        return new PageResult_24162131<>(items, p, size, total);
    }

    @Override
    public Video_24162131 viewDetail(String videoId) {
        Video_24162131 v = videoDao.findById(videoId);
        if (v == null) {
            return null;
        }
        videoDao.increaseViews(videoId);
        v.setViews((v.getViews() == null ? 0 : v.getViews()) + 1);
        fillCounts(List.of(v));
        return v;
    }

    @Override
    public List<CategoryBlock_24162131> buildHomeBlocks(List<Category_24162131> categories,
            Map<Integer, Integer> requestedPages, int size) {
        List<CategoryBlock_24162131> blocks = new ArrayList<>();
        for (Category_24162131 c : categories) {
            long total = videoDao.countActiveByCategory(c.getCategoryId());      // Câu 5
            int p = PageResult_24162131.clampPage(requestedPages.getOrDefault(c.getCategoryId(), 1), total, size);
            List<Video_24162131> items = videoDao.findActiveByCategory(c.getCategoryId(), p, size);
            fillCounts(items);

            CategoryBlock_24162131 b = new CategoryBlock_24162131();
            b.setCategory(c);
            b.setTotal(total);
            b.setPage(new PageResult_24162131<>(items, p, size, total));
            blocks.add(b);
        }
        // Mỗi khối giữ nguyên số trang của các category khác khi bấm chuyển trang
        for (CategoryBlock_24162131 b : blocks) {
            StringBuilder sb = new StringBuilder();
            for (CategoryBlock_24162131 o : blocks) {
                if (o != b) {
                    sb.append('p').append(o.getCategory().getCategoryId())
                            .append('=').append(o.getPage().getPage()).append('&');
                }
            }
            b.setOtherParams(sb.toString());
        }
        return blocks;
    }

    // ---------------- helper ----------------
    /** Gán số Share / Like cho từng video bằng 2 query count (tránh lazy-loading trong JSP). */
    private void fillCounts(List<Video_24162131> videos) {
        if (videos == null || videos.isEmpty()) {
            return;
        }
        List<String> ids = new ArrayList<>();
        for (Video_24162131 v : videos) {
            ids.add(v.getVideoId());
        }
        Map<String, Long> shares = videoDao.countSharesByVideo(ids);
        Map<String, Long> likes = videoDao.countLikesByVideo(ids);
        for (Video_24162131 v : videos) {
            v.setShareCount(shares.getOrDefault(v.getVideoId(), 0L));
            v.setLikeCount(likes.getOrDefault(v.getVideoId(), 0L));
        }
    }
}
