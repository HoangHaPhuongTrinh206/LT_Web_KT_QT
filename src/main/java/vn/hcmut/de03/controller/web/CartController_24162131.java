package vn.hcmut.de03.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmut.de03.entity.Video_24162131;
import vn.hcmut.de03.model.CartItem_24162131;
import vn.hcmut.de03.service.VideoService_24162131;
import vn.hcmut.de03.service.impl.VideoServiceImpl_24162131;

@WebServlet(name = "CartController_24162131", urlPatterns = { "/cart", "/cart/add", "/cart/remove", "/cart/update" })
public class CartController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VideoService_24162131 videoService = new VideoServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/cart/add".equals(path)) {
            addToCart(req, resp);
        } else if ("/cart/remove".equals(path)) {
            removeFromCart(req, resp);
        } else {
            // view cart
            req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/cart/update".equals(path)) {
            updateCart(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    @SuppressWarnings("unchecked")
    private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            Video_24162131 v = videoService.findById(id);
            // Chỉ cần active là thêm được; quantity null/0 = coi như không giới hạn
            int maxQty = (v != null && v.getQuantity() != null && v.getQuantity() > 0)
                    ? v.getQuantity() : Integer.MAX_VALUE;
            if (v != null && Boolean.TRUE.equals(v.getActive())) {
                HttpSession session = req.getSession();
                Map<String, CartItem_24162131> cart = (Map<String, CartItem_24162131>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new HashMap<>();
                }
                
                CartItem_24162131 item = cart.get(id);
                if (item != null) {
                    if (item.getQuantity() < maxQty) {
                        item.setQuantity(item.getQuantity() + 1);
                    }
                } else {
                    item = new CartItem_24162131();
                    item.setVideoId(v.getVideoId());
                    item.setTitle(v.getTitle());
                    item.setPoster(v.getPoster());
                    item.setPrice(v.getPrice() != null ? v.getPrice() : 0);
                    item.setQuantity(1);
                    item.setMaxQuantity(maxQty);
                    cart.put(id, item);
                }
                session.setAttribute("cart", cart);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @SuppressWarnings("unchecked")
    private void removeFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            HttpSession session = req.getSession();
            Map<String, CartItem_24162131> cart = (Map<String, CartItem_24162131>) session.getAttribute("cart");
            if (cart != null) {
                cart.remove(id);
                session.setAttribute("cart", cart);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @SuppressWarnings("unchecked")
    private void updateCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String qtyStr = req.getParameter("quantity");
        if (id != null && qtyStr != null) {
            try {
                int quantity = Integer.parseInt(qtyStr);
                HttpSession session = req.getSession();
                Map<String, CartItem_24162131> cart = (Map<String, CartItem_24162131>) session.getAttribute("cart");
                if (cart != null && cart.containsKey(id)) {
                    CartItem_24162131 item = cart.get(id);
                    if (quantity <= 0) {
                        cart.remove(id);
                    } else if (quantity <= item.getMaxQuantity()) {
                        item.setQuantity(quantity);
                    } else {
                        item.setQuantity(item.getMaxQuantity());
                    }
                    session.setAttribute("cart", cart);
                }
            } catch (NumberFormatException ignored) {}
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
