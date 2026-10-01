package vn.hcmut.de03.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import jakarta.servlet.http.Part;

/** Lưu file poster upload vào thư mục ngoài webapp (upload.dir) để không mất khi redeploy. */
public class UploadUtil_24162131 {
    private static final Set<String> ALLOWED = Set.of(".jpg", ".jpeg", ".png", ".gif", ".webp");

    private static final Set<String> VIDEO_EXT = Set.of(".mp4", ".webm");

    private UploadUtil_24162131() {
    }

    public static Path uploadDir() throws IOException {
        String dir = AppConfig_24162131.get("upload.dir", System.getProperty("user.home") + "/de03_uploads");
        Path p = Paths.get(dir);
        Files.createDirectories(p);
        return p;
    }

    /** Lưu file video thành <videoId>.mp4 / .webm trong thư mục upload (DB không có cột link video nên đặt tên theo mã video). */
    public static void saveVideo(Part part, String videoId) throws IOException {
        String submitted = part.getSubmittedFileName();
        int dot = submitted == null ? -1 : submitted.lastIndexOf('.');
        String ext = dot >= 0 ? submitted.substring(dot).toLowerCase() : "";
        if (!VIDEO_EXT.contains(ext)) {
            throw new IllegalArgumentException("File video chỉ hỗ trợ .mp4 hoặc .webm");
        }
        deleteVideo(videoId);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, uploadDir().resolve(videoId + ext), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /** @return tên file video của mã video (vd "V001.mp4") nếu có, ngược lại null */
    public static String findVideoFile(String videoId) {
        try {
            for (String ext : VIDEO_EXT) {
                if (Files.isRegularFile(uploadDir().resolve(videoId + ext))) {
                    return videoId + ext;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteVideo(String videoId) {
        try {
            for (String ext : VIDEO_EXT) {
                Files.deleteIfExists(uploadDir().resolve(videoId + ext));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** @return tên file đã lưu (ngắn, <= 50 ký tự để vừa cột Poster nvarchar(50)) */
    public static String save(Part part) throws IOException {
        String submitted = part.getSubmittedFileName();
        String ext = "";
        int dot = submitted == null ? -1 : submitted.lastIndexOf('.');
        if (dot >= 0) {
            ext = submitted.substring(dot).toLowerCase();
        }
        if (!ALLOWED.contains(ext)) {
            throw new IllegalArgumentException("Chỉ cho phép ảnh .jpg .jpeg .png .gif .webp");
        }
        String name = System.currentTimeMillis() + ext;
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, uploadDir().resolve(name), StandardCopyOption.REPLACE_EXISTING);
        }
        return name;
    }
}
