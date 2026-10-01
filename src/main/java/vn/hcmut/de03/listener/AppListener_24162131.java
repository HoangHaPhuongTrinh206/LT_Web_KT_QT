package vn.hcmut.de03.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.hcmut.de03.util.JpaUtil_24162131;

@WebListener
public class AppListener_24162131 implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil_24162131.close();
    }
}
