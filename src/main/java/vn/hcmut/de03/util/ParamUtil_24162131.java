package vn.hcmut.de03.util;

public class ParamUtil_24162131 {
    private ParamUtil_24162131() {
    }

    public static int toInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
