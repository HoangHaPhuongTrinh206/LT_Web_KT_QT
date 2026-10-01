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
import vn.hcmut.de03.util.AppConfig_24162131;
import vn.hcmut.de03.util.MailUtil_24162131;
import vn.hcmut.de03.util.OtpUtil_24162131;
import vn.hcmut.de03.util.ParamUtil_24162131;

/** Câu 2: Đăng ký -> lưu user (Active=false) -> gửi OTP -> chuyển sang /verify-otp. */
@WebServlet(name = "RegisterController_24162131", urlPatterns = { "/register" })
public class RegisterController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24162131 userService = new UserServiceImpl_24162131();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = ParamUtil_24162131.trim(req.getParameter("username"));
        String password = ParamUtil_24162131.trim(req.getParameter("password"));
        String confirm = ParamUtil_24162131.trim(req.getParameter("confirm"));
        String fullname = ParamUtil_24162131.trim(req.getParameter("fullname"));
        String email = ParamUtil_24162131.trim(req.getParameter("email"));
        String phone = ParamUtil_24162131.trim(req.getParameter("phone"));

        User_24162131 u = new User_24162131();
        u.setUsername(username);
        u.setFullname(fullname);
        u.setEmail(email);
        u.setPhone(phone);
        req.setAttribute("form", u);

        String error = null;
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            error = "Vui lòng nhập đầy đủ Tên đăng nhập, Mật khẩu, Họ tên, Email.";
        } else if (username.length() > 50 || password.length() > 50 || fullname.length() > 50) {
            error = "Tên đăng nhập, mật khẩu, họ tên tối đa 50 ký tự.";
        } else if (!email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            error = "Email không hợp lệ.";
        } else if (!phone.isEmpty() && !phone.matches("^\\d{9,15}$")) {
            error = "Số điện thoại phải gồm 9-15 chữ số.";
        } else if (!password.equals(confirm)) {
            error = "Mật khẩu xác nhận không khớp.";
        }
        if (error != null) {
            req.setAttribute("error", error);
            doGet(req, resp);
            return;
        }

        u.setPassword(password);
        try {
            userService.registerPending(u);
        } catch (IllegalStateException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
            return;
        }

        // ---- Sinh OTP + gửi mail ----
        String otp = OtpUtil_24162131.generate();
        int minutes = AppConfig_24162131.getInt("otp.expire.minutes", 5);
        HttpSession session = req.getSession(true);
        session.setAttribute("otpUsername", username);
        session.setAttribute("otpEmail", email);
        session.setAttribute("otpCode", otp);
        session.setAttribute("otpExpire", System.currentTimeMillis() + minutes * 60_000L);

        VerifyOtpController_24162131.deliver(session, email, otp);
        resp.sendRedirect(req.getContextPath() + "/verify-otp");
    }
}
