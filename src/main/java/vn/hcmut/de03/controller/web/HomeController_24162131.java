package vn.hcmut.de03.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.model.CategoryBlock_24162131;
import vn.hcmut.de03.service.CategoryService_24162131;
import vn.hcmut.de03.service.VideoService_24162131;
import vn.hcmut.de03.service.impl.CategoryServiceImpl_24162131;
import vn.hcmut.de03.service.impl.VideoServiceImpl_24162131;
import vn.hcmut.de03.util.ParamUtil_24162131;

/** Câu 4 + Câu 5: trang home của User - video theo từng category, 3 video/trang, kèm số lượng video. */
@WebServlet(name = "HomeController_24162131", urlPatterns = { "", "/home" })
public class HomeController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 3;

    private final CategoryService_24162131 categoryService = new CategoryServiceImpl_24162131();
    private final VideoService_24162131 videoService = new VideoServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category_24162131> categories = categoryService.findActive();

        // Mỗi category có tham số trang riêng: p<CategoryId>=<số trang>
        Map<Integer, Integer> pages = new HashMap<>();
        for (Category_24162131 c : categories) {
            pages.put(c.getCategoryId(), ParamUtil_24162131.toInt(req.getParameter("p" + c.getCategoryId()), 1));
        }

        List<CategoryBlock_24162131> blocks = videoService.buildHomeBlocks(categories, pages, PAGE_SIZE);
        req.setAttribute("blocks", blocks);
        req.getRequestDispatcher("/WEB-INF/views/web/home.jsp").forward(req, resp);
    }
}
