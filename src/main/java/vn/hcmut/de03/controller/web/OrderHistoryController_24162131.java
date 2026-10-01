package vn.hcmut.de03.controller.web;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmut.de03.entity.Order_24162131;
import vn.hcmut.de03.entity.User_24162131;
import vn.hcmut.de03.service.OrderService_24162131;
import vn.hcmut.de03.service.impl.OrderServiceImpl_24162131;
import vn.hcmut.de03.util.ParamUtil_24162131;

@WebServlet(name = "OrderHistoryController_24162131", urlPatterns = { "/order-history" })
public class OrderHistoryController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService_24162131 orderService = new OrderServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User_24162131 user = (User_24162131) session.getAttribute("sessionUser");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=Vui long dang nhap");
            return;
        }

        String statusStr = req.getParameter("status");
        List<Order_24162131> orders;
        if (statusStr != null && !statusStr.isEmpty()) {
            Integer status = ParamUtil_24162131.toInt(statusStr, -1);
            if (status != -1) {
                orders = orderService.findByUserAndStatus(user.getUsername(), status);
                req.setAttribute("selectedStatus", status);
            } else {
                orders = orderService.findByUser(user.getUsername());
            }
        } else {
            orders = orderService.findByUser(user.getUsername());
        }

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/web/order-history.jsp").forward(req, resp);
    }
}
