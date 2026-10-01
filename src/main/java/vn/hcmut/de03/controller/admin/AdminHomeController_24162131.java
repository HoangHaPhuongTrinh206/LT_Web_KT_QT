package vn.hcmut.de03.controller.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmut.de03.service.CategoryService_24162131;
import vn.hcmut.de03.service.VideoService_24162131;
import vn.hcmut.de03.service.impl.CategoryServiceImpl_24162131;
import vn.hcmut.de03.service.impl.VideoServiceImpl_24162131;

/** Trang chủ của Admin (đã được AdminFilter bảo vệ). */
@WebServlet(name = "AdminHomeController_24162131", urlPatterns = { "/admin", "/admin/home" })
public class AdminHomeController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final VideoService_24162131 videoService = new VideoServiceImpl_24162131();
    private final CategoryService_24162131 categoryService = new CategoryServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("totalVideos", videoService.countAll());
        req.setAttribute("totalCategories", categoryService.findAll().size());
        req.getRequestDispatcher("/WEB-INF/views/admin/home.jsp").forward(req, resp);
    }
}
