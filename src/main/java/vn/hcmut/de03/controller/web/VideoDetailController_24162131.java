package vn.hcmut.de03.controller.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmut.de03.entity.Video_24162131;
import vn.hcmut.de03.service.VideoService_24162131;
import vn.hcmut.de03.service.impl.VideoServiceImpl_24162131;

/** Câu 3: trang chi tiết 1 video  ->  /video?id=V001 */
@WebServlet(name = "VideoDetailController_24162131", urlPatterns = { "/video" })
public class VideoDetailController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final VideoService_24162131 videoService = new VideoServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Video_24162131 video = (id == null || id.isBlank()) ? null : videoService.viewDetail(id.trim());
        if (video == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        req.setAttribute("video", video);
        // Nguồn phát: file mp4/webm đã upload (tên = mã video) hoặc mã YouTube 11 ký tự đặt làm VideoId
        String file = vn.hcmut.de03.util.UploadUtil_24162131.findVideoFile(video.getVideoId());
        req.setAttribute("videoFile", file);
        if (file == null && video.getVideoId().matches("^[A-Za-z0-9_-]{11}$")) {
            req.setAttribute("youtubeId", video.getVideoId());
        }
        req.getRequestDispatcher("/WEB-INF/views/web/video-detail.jsp").forward(req, resp);
    }
}
