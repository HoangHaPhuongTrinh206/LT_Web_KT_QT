package vn.hcmut.de03.controller.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hcmut.de03.service.UserService_24162131;
import vn.hcmut.de03.service.impl.UserServiceImpl_24162131;
import vn.hcmut.de03.util.AppConfig_24162131;
import vn.hcmut.de03.util.MailUtil_24162131;
import vn.hcmut.de03.util.OtpUtil_24162131;
import vn.hcmut.de03.util.ParamUtil_24162131;

/** Câu 2: nhập OTP để kích hoạt tài khoản (Active = true). */
@WebServlet(name = "VerifyOtpController_24162131", urlPatterns = { "/verify-otp" })
public class VerifyOtpController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24162131 userService = new UserServiceImpl_24162131();

    /** Gửi OTP qua mail; nếu không gửi được thì in ra console (và hiện trên trang nếu otp.demo=true). */
    static void deliver(HttpSession session, String email, String otp) {
        boolean sent = MailUtil_24162131.send(email, "Mã OTP kích hoạt tài khoản",
                "Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong "
                        + AppConfig_24162131.getInt("otp.expire.minutes", 5) + " phút.");
        if (sent) {
            session.setAttribute("flash", "Đã gửi mã OTP tới " + email + ". Vui lòng kiểm tra hộp thư.");
            session.removeAttribute("demoOtp");
        } else {
            System.out.println("[OTP] " + email + " -> " + otp);
            session.setAttribute("flash", "Chưa gửi được email (kiểm tra cấu hình app.properties). OTP được in ở console server.");
            if (AppConfig_24162131.getBool("otp.demo", true)) {
                session.setAttribute("demoOtp", otp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("otpUsername") == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }
        Object flash = session.getAttribute("flash");
        if (flash != null) {
            req.setAttribute("success", flash);
            session.removeAttribute("flash");
        }
        req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("otpUsername") == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }
        String username = (String) session.getAttribute("otpUsername");
        String email = (String) session.getAttribute("otpEmail");

        // Gửi lại OTP
        if ("resend".equals(req.getParameter("action"))) {
            String otp = OtpUtil_24162131.generate();
            session.setAttribute("otpCode", otp);
            session.setAttribute("otpExpire",
                    System.currentTimeMillis() + AppConfig_24162131.getInt("otp.expire.minutes", 5) * 60_000L);
            deliver(session, email, otp);
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
            return;
        }

        String input = ParamUtil_24162131.trim(req.getParameter("otp"));
        String otp = (String) session.getAttribute("otpCode");
        Long expire = (Long) session.getAttribute("otpExpire");

        if (expire == null || System.currentTimeMillis() > expire) {
            req.setAttribute("error", "Mã OTP đã hết hạn, hãy bấm \"Gửi lại OTP\".");
        } else if (otp == null || !otp.equals(input)) {
            req.setAttribute("error", "Mã OTP không đúng.");
        } else {
            userService.activate(username);
            session.removeAttribute("otpUsername");
            session.removeAttribute("otpEmail");
            session.removeAttribute("otpCode");
            session.removeAttribute("otpExpire");
            session.removeAttribute("demoOtp");
            resp.sendRedirect(req.getContextPath() + "/login?registered=1");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
    }
}
