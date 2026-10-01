package vn.hcmut.de03.controller.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmut.de03.service.VideoService_24162131;
import vn.hcmut.de03.service.impl.VideoServiceImpl_24162131;
import vn.hcmut.de03.util.ParamUtil_24162131;

/** Menu "Sản phẩm": tất cả video đang Active, 6 video/trang. */
@WebServlet(name = "ProductController_24162131", urlPatterns = { "/products" })
public class ProductController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 6;

    private final VideoService_24162131 videoService = new VideoServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = ParamUtil_24162131.toInt(req.getParameter("page"), 1);
        req.setAttribute("pg", videoService.activePage(page, PAGE_SIZE));
        req.getRequestDispatcher("/WEB-INF/views/web/products.jsp").forward(req, resp);
    }
}
