package vn.hcmut.de03.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/** Đọc file app.properties (src/main/resources). */
public class AppConfig_24162131 {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = AppConfig_24162131.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (in != null) {
                PROPS.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AppConfig_24162131() {
    }

    public static String get(String key, String defaultValue) {
        String v = PROPS.getProperty(key);
        return (v == null || v.isBlank()) ? defaultValue : v.trim();
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = PROPS.getProperty(key);
        return (v == null || v.isBlank()) ? defaultValue : Boolean.parseBoolean(v.trim());
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
