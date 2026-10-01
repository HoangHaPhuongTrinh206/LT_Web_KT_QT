# Đề 03 – Lập Trình Web – Hoàng Hà Phương Trinh – 24162131

Maven WAR | Java 17 | Tomcat 10.0 / 10.1 đều chạy được (Servlet 5.0, JSTL 2.0) | Servlet + JSP + JSTL + JPA (Hibernate 6) + SiteMesh 3 | SQL Server

## Chạy project (Spring Tool Suite)
1. SSMS: mở `database.sql` → Execute (tạo DB `De03_24162131` + bảng + dữ liệu mẫu).
2. Sửa `src/main/resources/META-INF/persistence.xml` (user/password SQL Server; bật TCP/IP port 1433).
3. STS: File > Import > Maven > Existing Maven Projects → chọn thư mục `24162131_made`.
4. Chuột phải project > Maven > Update Project (Force Update).
5. Chuột phải project > Run As > Run on Server > Tomcat v10.x.
6. Mở http://localhost:8080/24162131_made/

Tài khoản mẫu: `admin / admin123` (admin) – `user1 / 123456` (user).

## Cấu hình OTP (Câu 2)
`src/main/resources/app.properties`: `mail.enabled=true` + Gmail + App Password để gửi mail thật.
Để `mail.enabled=false` (mặc định) thì OTP hiện ngay trên trang xác thực và in ở Console (otp.demo=true).

## Bảng URL
| URL | Chức năng |
|---|---|
| /home (hoặc /) | Home User: video theo category, 3 video/trang, "Category (số video)" – Câu 4, 5 |
| /products | Sản phẩm: tất cả video, 6/trang |
| /video?id=V001 | Chi tiết video – Câu 3 |
| /register, /verify-otp | Đăng ký + kích hoạt OTP |
| /login, /logout | Đăng nhập / đăng xuất (Session) |
| /admin/home | Trang chủ Admin |
| /admin/videos | CRUD Video, 6 video/trang |

## Quy ước tên
Mọi class/interface đều đặt tên `Ten_24162131.java` (theo đề).

## Phát video (Câu 3)
Bảng Videos không có cột link video, nên có 2 cách cho trang chi tiết phát được video:
1. Admin > Sửa video > "File video" chọn .mp4/.webm (lưu tên theo mã video trong ~/de03_uploads).
2. Khi THÊM video, nhập Mã video = ID YouTube 11 ký tự (vd dQw4w9WgXcQ) -> trang chi tiết nhúng player YouTube.
Không có cả hai thì chỉ hiện poster.

## Lỗi font
Đã ép UTF-8 cho *.jsp/*.jspf trong web.xml. database.sql lưu UTF-8 có BOM; nếu vẫn lỗi hãy chạy lại file SQL (nó xóa và tạo lại bảng).
