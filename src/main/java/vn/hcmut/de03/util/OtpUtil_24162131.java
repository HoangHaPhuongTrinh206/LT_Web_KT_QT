package vn.hcmut.de03.util;

import java.security.SecureRandom;

public class OtpUtil_24162131 {
    private static final SecureRandom RANDOM = new SecureRandom();

    private OtpUtil_24162131() {
    }

    /** Sinh OTP 6 chữ số. */
    public static String generate() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }
}
