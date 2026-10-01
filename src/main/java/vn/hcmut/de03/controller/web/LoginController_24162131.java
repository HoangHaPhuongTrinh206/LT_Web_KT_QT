package vn.hcmut.de03.controller.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmut.de03.entity.User_24162131;
import vn.hcmut.de03.service.UserService_24162131;
import vn.hcmut.de03.service.impl.UserServiceImpl_24162131;
import vn.hcmut.de03.util.ParamUtil_24162131;

/** Câu 2: Đăng nhập (Session). Admin -> /admin/home ; ngược lại -> quay lại trang đăng nhập / trang chủ. */
@WebServlet(name = "LoginController_24162131", urlPatterns = { "/login" })
public class LoginController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24162131 userService = new UserServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = req.getParameter("error");
        if ("admin".equals(error)) {
            req.setAttribute("error", "Bạn cần đăng nhập bằng tài khoản admin để vào trang quản trị.");
        }
        if (req.getParameter("registered") != null) {
            req.setAttribute("success", "Kích hoạt tài khoản thành công, hãy đăng nhập.");
        }
        req.getRequestDispatcher("/WEB-INF/views/web/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = ParamUtil_24162131.trim(req.getParameter("username"));
        String password = ParamUtil_24162131.trim(req.getParameter("password"));

        User_24162131 user = userService.authenticate(username, password);

        if (user == null) {
            fail(req, resp, username, "Sai tên đăng nhập hoặc mật khẩu.");
            return;
        }
        if (!Boolean.TRUE.equals(user.getActive())) {
            fail(req, resp, username, "Tài khoản chưa được kích hoạt (hãy hoàn tất bước nhập OTP khi đăng ký).");
            return;
        }

        user.setPassword(null);                     // không giữ mật khẩu trong session
        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", user);

        if (Boolean.TRUE.equals(user.getAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");    // admin -> trang chủ Admin
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");          // user thường -> trang chủ User
        }
    }

    private void fail(HttpServletRequest req, HttpServletResponse resp, String username, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.setAttribute("username", username);
        req.getRequestDispatcher("/WEB-INF/views/web/login.jsp").forward(req, resp);   // quay lại trang đăng nhập
    }
}
