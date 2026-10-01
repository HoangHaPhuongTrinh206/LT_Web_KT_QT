package vn.hcmut.de03.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmut.de03.entity.User_24162131;
import vn.hcmut.de03.model.CartItem_24162131;
import vn.hcmut.de03.service.OrderService_24162131;
import vn.hcmut.de03.service.impl.OrderServiceImpl_24162131;

@WebServlet(name = "CheckoutController_24162131", urlPatterns = { "/checkout" })
public class CheckoutController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService_24162131 orderService = new OrderServiceImpl_24162131();

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User_24162131 user = (User_24162131) session.getAttribute("sessionUser");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=Vui long dang nhap de thanh toan");
            return;
        }

        Map<String, CartItem_24162131> cart = (Map<String, CartItem_24162131>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            req.setAttribute("message", "Giỏ hàng trống!");
            req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
            return;
        }

        String paymentMethod = req.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            paymentMethod = "COD";
        }

        boolean success = orderService.checkout(user, new ArrayList<>(cart.values()), paymentMethod);
        if (success) {
            session.removeAttribute("cart");
            req.setAttribute("message", "Đặt hàng thành công!");
            req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "Đặt hàng thất bại (có thể do hết hàng)!");
            req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
        }
    }
}
