package vn.hcmut.de03.controller.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.entity.Video_24162131;
import vn.hcmut.de03.service.CategoryService_24162131;
import vn.hcmut.de03.service.VideoService_24162131;
import vn.hcmut.de03.service.impl.CategoryServiceImpl_24162131;
import vn.hcmut.de03.service.impl.VideoServiceImpl_24162131;
import vn.hcmut.de03.util.ParamUtil_24162131;
import vn.hcmut.de03.util.UploadUtil_24162131;

/**
 * Câu 2 (2.5đ): CRUD bảng Videos, phân trang 6 video / trang.
 *   GET  /admin/videos?page=1     -> danh sách (Read)
 *   GET  /admin/videos/add        -> form thêm
 *   GET  /admin/videos/edit?id=   -> form sửa
 *   POST /admin/videos/save       -> lưu (Create / Update)
 *   POST /admin/videos/delete     -> xóa (Delete)
 */
@WebServlet(name = "AdminVideoController_24162131", urlPatterns = { "/admin/videos/*" })
@MultipartConfig(maxFileSize = 300L * 1024 * 1024, maxRequestSize = 310L * 1024 * 1024)
public class AdminVideoController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 6;
    private static final String VIEW_LIST = "/WEB-INF/views/admin/video-list.jsp";
    private static final String VIEW_FORM = "/WEB-INF/views/admin/video-form.jsp";

    private final VideoService_24162131 videoService = new VideoServiceImpl_24162131();
    private final CategoryService_24162131 categoryService = new CategoryServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        switch (path) {
        case "/add": {
            Video_24162131 v = new Video_24162131();
            v.setActive(true);
            v.setViews(0);
            showForm(req, resp, v, false);
            break;
        }
        case "/edit": {
            Video_24162131 v = videoService.findById(ParamUtil_24162131.trim(req.getParameter("id")));
            if (v == null) {
                flash(req, "Không tìm thấy video.");
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                return;
            }
            showForm(req, resp, v, true);
            break;
        }
        default:
            showList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        if ("/save".equals(path)) {
            save(req, resp);
        } else if ("/delete".equals(path)) {
            String id = ParamUtil_24162131.trim(req.getParameter("id"));
            int page = ParamUtil_24162131.toInt(req.getParameter("page"), 1);
            videoService.delete(id);
            UploadUtil_24162131.deleteVideo(id);
            flash(req, "Đã xóa video " + id);
            resp.sendRedirect(req.getContextPath() + "/admin/videos?page=" + page);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }

    // ---------------------------------------------------------------- list
    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object f = session.getAttribute("flash");
        if (f != null) {
            req.setAttribute("flash", f);
            session.removeAttribute("flash");
        }
        int page = ParamUtil_24162131.toInt(req.getParameter("page"), 1);
        req.setAttribute("pg", videoService.adminPage(page, PAGE_SIZE));
        req.getRequestDispatcher(VIEW_LIST).forward(req, resp);
    }

    // ---------------------------------------------------------------- form
    private void showForm(HttpServletRequest req, HttpServletResponse resp, Video_24162131 v, boolean edit)
            throws ServletException, IOException {
        req.setAttribute("video", v);
        req.setAttribute("edit", edit);
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher(VIEW_FORM).forward(req, resp);
    }

    // ---------------------------------------------------------------- save (create / update)
    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean edit = "true".equals(req.getParameter("edit"));

        Video_24162131 v = new Video_24162131();
        v.setVideoId(ParamUtil_24162131.trim(req.getParameter("videoId")));
        v.setTitle(ParamUtil_24162131.trim(req.getParameter("title")));
        v.setDescription(ParamUtil_24162131.trim(req.getParameter("description")));
        v.setViews(Math.max(0, ParamUtil_24162131.toInt(req.getParameter("views"), 0)));
        v.setActive(req.getParameter("active") != null);

        int categoryId = ParamUtil_24162131.toInt(req.getParameter("categoryId"), -1);
        Category_24162131 c = new Category_24162131();
        c.setCategoryId(categoryId);
        v.setCategory(c);

        // Poster: ưu tiên file upload, nếu không có thì lấy link/tên nhập tay, không có gì thì giữ poster cũ
        String posterText = ParamUtil_24162131.trim(req.getParameter("posterUrl"));
        String error = null;
        String poster = null;
        try {
            Part file = req.getPart("posterFile");
            if (file != null && file.getSize() > 0) {
                poster = UploadUtil_24162131.save(file);
            } else if (!posterText.isEmpty()) {
                poster = posterText;
            }
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        if (poster != null && poster.length() > 50) {
            error = "Poster tối đa 50 ký tự (cột Poster là nvarchar(50)).";
        }
        v.setPoster(poster);

        if (error == null && v.getTitle().isEmpty()) {
            error = "Tiêu đề không được để trống.";
        } else if (error == null && v.getTitle().length() > 200) {
            error = "Tiêu đề tối đa 200 ký tự.";
        } else if (error == null && v.getDescription().length() > 500) {
            error = "Mô tả tối đa 500 ký tự.";
        } else if (error == null && categoryId < 0) {
            error = "Vui lòng chọn Category.";
        }

        if (error != null) {
            if (edit) {                                    // giữ lại poster hiện tại để hiển thị trên form
                Video_24162131 old = videoService.findById(v.getVideoId());
                if (old != null && v.getPoster() == null) {
                    v.setPoster(old.getPoster());
                }
            }
            req.setAttribute("error", error);
            showForm(req, resp, v, edit);
            return;
        }

        // Mã video nhập tay (tùy chọn, chỉ khi thêm mới): chỉ cho chữ, số, _ và -
        if (!edit && !v.getVideoId().isEmpty() && !v.getVideoId().matches("^[A-Za-z0-9_-]{1,50}$")) {
            req.setAttribute("error", "Mã video chỉ gồm chữ, số, _ và - (tối đa 50 ký tự).");
            showForm(req, resp, v, edit);
            return;
        }

        try {
            if (edit) {
                videoService.update(v);
            } else {
                videoService.create(v);
            }
            Part videoFile = req.getPart("videoFile");
            if (videoFile != null && videoFile.getSize() > 0) {
                UploadUtil_24162131.saveVideo(videoFile, v.getVideoId());
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            showForm(req, resp, v, edit);
            return;
        }
        flash(req, (edit ? "Đã cập nhật video " : "Đã thêm video mới ") + v.getVideoId());
        resp.sendRedirect(req.getContextPath() + "/admin/videos");
    }

    private void flash(HttpServletRequest req, String message) {
        req.getSession().setAttribute("flash", message);
    }
}
