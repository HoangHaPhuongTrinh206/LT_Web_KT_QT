package vn.hcmut.de03.util;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailUtil_24162131 {
    private MailUtil_24162131() {
    }

    /** @return true nếu gửi mail thành công; false nếu tắt mail (mail.enabled=false) hoặc gửi lỗi */
    public static boolean send(String to, String subject, String content) {
        if (!AppConfig_24162131.getBool("mail.enabled", false)) {
            return false;
        }
        final String user = AppConfig_24162131.get("mail.username", "");
        final String pass = AppConfig_24162131.get("mail.password", "");
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", AppConfig_24162131.get("mail.host", "smtp.gmail.com"));
            p.put("mail.smtp.port", AppConfig_24162131.get("mail.port", "587"));
            p.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session session = Session.getInstance(p, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject, "UTF-8");
            msg.setText(content, "UTF-8");
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
