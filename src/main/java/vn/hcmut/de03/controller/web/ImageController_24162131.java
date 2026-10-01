package vn.hcmut.de03.controller.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmut.de03.util.UploadUtil_24162131;

/** Trả ảnh poster đã upload:  /uploads/<tên file> */
@WebServlet(name = "ImageController_24162131", urlPatterns = { "/uploads/*" })
public class ImageController_24162131 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String info = req.getPathInfo();
        if (info == null || info.length() < 2) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String name = Paths.get(info).getFileName().toString();      // chống ../ (path traversal)
        Path file = UploadUtil_24162131.uploadDir().resolve(name);
        if (!Files.isRegularFile(file)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String type = getServletContext().getMimeType(name);
        if (type == null && name.endsWith(".webm")) type = "video/webm";
        if (type == null && name.endsWith(".mp4")) type = "video/mp4";
        resp.setContentType(type != null ? type : "application/octet-stream");
        resp.setHeader("Accept-Ranges", "bytes");

        long size = Files.size(file);
        long from = 0, to = size - 1;
        String range = req.getHeader("Range");                     // trình duyệt xin từng đoạn khi phát/tua video
        if (range != null && range.startsWith("bytes=")) {
            try {
                String[] p = range.substring(6).split("-", 2);
                if (!p[0].isEmpty()) from = Long.parseLong(p[0]);
                if (p.length > 1 && !p[1].isEmpty()) to = Math.min(Long.parseLong(p[1]), size - 1);
                resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                resp.setHeader("Content-Range", "bytes " + from + "-" + to + "/" + size);
            } catch (NumberFormatException e) {
                from = 0;
                to = size - 1;
            }
        }
        resp.setContentLengthLong(to - from + 1);
        try (InputStream in = Files.newInputStream(file); OutputStream out = resp.getOutputStream()) {
            in.skipNBytes(from);
            byte[] buf = new byte[16 * 1024];
            long left = to - from + 1;
            int n;
            while (left > 0 && (n = in.read(buf, 0, (int) Math.min(buf.length, left))) > 0) {
                out.write(buf, 0, n);
                left -= n;
            }
        }
    }
}
