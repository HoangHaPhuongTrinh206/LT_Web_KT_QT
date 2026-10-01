/* ============================================================
   Đề thi quá trình - Lập Trình Web - Đề 03
   SV: Hoàng Hà Phương Trinh - MSSV: 24162131
   ============================================================ */
IF DB_ID(N'De03_24162131') IS NULL
    CREATE DATABASE De03_24162131;
GO
USE De03_24162131;
GO

IF OBJECT_ID('dbo.Shares','U')    IS NOT NULL DROP TABLE dbo.Shares;
IF OBJECT_ID('dbo.Favorites','U') IS NOT NULL DROP TABLE dbo.Favorites;
IF OBJECT_ID('dbo.Videos','U')    IS NOT NULL DROP TABLE dbo.Videos;
IF OBJECT_ID('dbo.Category','U')  IS NOT NULL DROP TABLE dbo.Category;
IF OBJECT_ID('dbo.Users','U')     IS NOT NULL DROP TABLE dbo.Users;
GO

CREATE TABLE dbo.Users (
    Username  NVARCHAR(50)  NOT NULL PRIMARY KEY,
    Password  NVARCHAR(50)  NULL,
    Phone     NVARCHAR(15)  NULL,
    Fullname  NVARCHAR(50)  NULL,
    Email     NVARCHAR(150) NULL,
    Admin     BIT           NULL,
    Active    BIT           NULL,
    Images    NVARCHAR(500) NULL
);

CREATE TABLE dbo.Category (
    CategoryId   INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Categoryname NVARCHAR(100) NULL,
    Categorycode NVARCHAR(100) NULL,
    Images       NVARCHAR(500) NULL,
    Status       BIT           NULL
);

CREATE TABLE dbo.Videos (
    VideoId     NVARCHAR(50)  NOT NULL PRIMARY KEY,
    Title       NVARCHAR(200) NULL,
    Poster      NVARCHAR(50)  NULL,
    Views       INT           NULL,
    Description NVARCHAR(500) NULL,
    Active      BIT           NULL,
    CategoryId  INT           NULL,
    Price       FLOAT         NULL,
    Quantity    INT           NULL,
    CONSTRAINT FK_Videos_Category FOREIGN KEY (CategoryId) REFERENCES dbo.Category(CategoryId)
);

CREATE TABLE dbo.Shares (
    ShareId    INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Emails     NVARCHAR(50) NULL,
    SharedDate DATE         NULL,
    Username   NVARCHAR(50) NULL,
    VideoId    NVARCHAR(50) NULL,
    CONSTRAINT FK_Shares_Users  FOREIGN KEY (Username) REFERENCES dbo.Users(Username),
    CONSTRAINT FK_Shares_Videos FOREIGN KEY (VideoId)  REFERENCES dbo.Videos(VideoId)
);

CREATE TABLE dbo.Favorites (
    FavoriteId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    LikedDate  DATE         NULL,
    VideoId    NVARCHAR(50) NULL,
    Username   NVARCHAR(50) NULL,
    CONSTRAINT FK_Favorites_Users  FOREIGN KEY (Username) REFERENCES dbo.Users(Username),
    CONSTRAINT FK_Favorites_Videos FOREIGN KEY (VideoId)  REFERENCES dbo.Videos(VideoId)
);

CREATE TABLE dbo.Orders (
    OrderId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    OrderDate DATETIME NULL,
    Status INT NULL,
    TotalAmount FLOAT NULL,
    PaymentMethod NVARCHAR(50) NULL,
    CONSTRAINT FK_Orders_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username)
);

CREATE TABLE dbo.OrderDetails (
    OrderDetailId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    OrderId INT NOT NULL,
    VideoId NVARCHAR(50) NOT NULL,
    Quantity INT NULL,
    Price FLOAT NULL,
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderId) REFERENCES dbo.Orders(OrderId),
    CONSTRAINT FK_OrderDetails_Videos FOREIGN KEY (VideoId) REFERENCES dbo.Videos(VideoId)
);
GO

/* ---------- Dữ liệu mẫu ---------- */
-- Tài khoản: admin / admin123 (quyền admin) ; user1..user3 / 123456 (user thường)
INSERT INTO dbo.Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images) VALUES
(N'admin', N'admin123', N'0900000001', N'Quản trị viên',        N'admin@example.com', 1, 1, NULL),
(N'user1', N'123456',   N'0900000002', N'Nguyễn Văn A',         N'user1@example.com', 0, 1, NULL),
(N'user2', N'123456',   N'0900000003', N'Trần Thị B',           N'user2@example.com', 0, 1, NULL),
(N'user3', N'123456',   N'0900000004', N'Lê Văn C',             N'user3@example.com', 0, 1, NULL);


INSERT INTO dbo.Category (Categoryname, Categorycode, Images, Status) VALUES
(N'Âm nhạc', N'MUSIC', NULL, 1),
(N'Giáo dục', N'EDU', NULL, 1),
(N'Giải trí', N'ENT', NULL, 1),
(N'Thể thao', N'SPORT', NULL, 1);

INSERT INTO dbo.Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId, Price, Quantity) VALUES
(N'V001', N'Nhạc Lofi thư giãn', N'https://picsum.photos/seed/v1/300/200', 187, N'Mô tả cho video "Nhạc Lofi thư giãn" thuộc danh mục Âm nhạc.', 1, 1, 10000, 50),
(N'V002', N'Top hit Vpop 2026', N'https://picsum.photos/seed/v2/300/200', 324, N'Mô tả cho video "Top hit Vpop 2026" thuộc danh mục Âm nhạc.', 1, 1, 15000, 30),
(N'V003', N'Acoustic cover chill', N'https://picsum.photos/seed/v3/300/200', 461, N'Mô tả cho video "Acoustic cover chill" thuộc danh mục Âm nhạc.', 1, 1, 20000, 20),
(N'V004', N'Nhạc không lời học bài', N'https://picsum.photos/seed/v4/300/200', 598, N'Mô tả cho video "Nhạc không lời học bài" thuộc danh mục Âm nhạc.', 1, 1, 12000, 40),
(N'V005', N'Live concert mùa hè', N'https://picsum.photos/seed/v5/300/200', 735, N'Mô tả cho video "Live concert mùa hè" thuộc danh mục Âm nhạc.', 1, 1, 25000, 15),
(N'V006', N'Rap Việt tuyển chọn', N'https://picsum.photos/seed/v6/300/200', 872, N'Mô tả cho video "Rap Việt tuyển chọn" thuộc danh mục Âm nhạc.', 1, 1, 18000, 25),
(N'V007', N'Piano nhẹ nhàng', N'https://picsum.photos/seed/v7/300/200', 109, N'Mô tả cho video "Piano nhẹ nhàng" thuộc danh mục Âm nhạc.', 1, 1, 15000, 30),
(N'V008', N'EDM sôi động', N'https://picsum.photos/seed/v8/300/200', 246, N'Mô tả cho video "EDM sôi động" thuộc danh mục Âm nhạc.', 1, 1, 20000, 10),
(N'V009', N'Học Servlet từ đầu', N'https://picsum.photos/seed/v9/300/200', 383, N'Mô tả cho video "Học Servlet từ đầu" thuộc danh mục Giáo dục.', 1, 2, 50000, 100),
(N'V010', N'JPA & Hibernate cơ bản', N'https://picsum.photos/seed/v10/300/200', 520, N'Mô tả cho video "JPA & Hibernate cơ bản" thuộc danh mục Giáo dục.', 1, 2, 60000, 100),
(N'V011', N'SQL Server thực chiến', N'https://picsum.photos/seed/v11/300/200', 657, N'Mô tả cho video "SQL Server thực chiến" thuộc danh mục Giáo dục.', 1, 2, 55000, 80),
(N'V012', N'Spring Boot REST API', N'https://picsum.photos/seed/v12/300/200', 794, N'Mô tả cho video "Spring Boot REST API" thuộc danh mục Giáo dục.', 1, 2, 70000, 50),
(N'V013', N'Tiếng Anh giao tiếp', N'https://picsum.photos/seed/v13/300/200', 931, N'Mô tả cho video "Tiếng Anh giao tiếp" thuộc danh mục Giáo dục.', 1, 2, 40000, 200),
(N'V014', N'Toán rời rạc dễ hiểu', N'https://picsum.photos/seed/v14/300/200', 168, N'Mô tả cho video "Toán rời rạc dễ hiểu" thuộc danh mục Giáo dục.', 1, 2, 35000, 150),
(N'V015', N'Git & GitHub cho người mới', N'https://picsum.photos/seed/v15/300/200', 305, N'Mô tả cho video "Git & GitHub cho người mới" thuộc danh mục Giáo dục.', 1, 2, 30000, 100),
(N'V016', N'Docker trong 30 phút', N'https://picsum.photos/seed/v16/300/200', 442, N'Mô tả cho video "Docker trong 30 phút" thuộc danh mục Giáo dục.', 1, 2, 45000, 60),
(N'V017', N'Phim ngắn cuối tuần', N'https://picsum.photos/seed/v17/300/200', 579, N'Mô tả cho video "Phim ngắn cuối tuần" thuộc danh mục Giải trí.', 1, 3, 20000, 40),
(N'V018', N'Hài kịch đặc sắc', N'https://picsum.photos/seed/v18/300/200', 716, N'Mô tả cho video "Hài kịch đặc sắc" thuộc danh mục Giải trí.', 1, 3, 15000, 50),
(N'V019', N'Vlog du lịch Đà Lạt', N'https://picsum.photos/seed/v19/300/200', 853, N'Mô tả cho video "Vlog du lịch Đà Lạt" thuộc danh mục Giải trí.', 1, 3, 25000, 30),
(N'V020', N'Review phim hot', N'https://picsum.photos/seed/v20/300/200', 90, N'Mô tả cho video "Review phim hot" thuộc danh mục Giải trí.', 1, 3, 10000, 100),
(N'V021', N'Bí quyết nấu ăn', N'https://picsum.photos/seed/v21/300/200', 227, N'Mô tả cho video "Bí quyết nấu ăn" thuộc danh mục Giải trí.', 1, 3, 15000, 80),
(N'V022', N'Thử thách 24 giờ', N'https://picsum.photos/seed/v22/300/200', 364, N'Mô tả cho video "Thử thách 24 giờ" thuộc danh mục Giải trí.', 1, 3, 18000, 50),
(N'V023', N'Hậu trường show', N'https://picsum.photos/seed/v23/300/200', 501, N'Mô tả cho video "Hậu trường show" thuộc danh mục Giải trí.', 1, 3, 20000, 40),
(N'V024', N'Tổng hợp meme vui', N'https://picsum.photos/seed/v24/300/200', 638, N'Mô tả cho video "Tổng hợp meme vui" thuộc danh mục Giải trí.', 1, 3, 5000, 200),
(N'V025', N'Highlight bóng đá', N'https://picsum.photos/seed/v25/300/200', 775, N'Mô tả cho video "Highlight bóng đá" thuộc danh mục Thể thao.', 1, 4, 15000, 60),
(N'V026', N'Bóng rổ đỉnh cao', N'https://picsum.photos/seed/v26/300/200', 912, N'Mô tả cho video "Bóng rổ đỉnh cao" thuộc danh mục Thể thao.', 1, 4, 20000, 40),
(N'V027', N'Hướng dẫn chạy bộ', N'https://picsum.photos/seed/v27/300/200', 149, N'Mô tả cho video "Hướng dẫn chạy bộ" thuộc danh mục Thể thao.', 1, 4, 10000, 100),
(N'V028', N'Yoga buổi sáng', N'https://picsum.photos/seed/v28/300/200', 286, N'Mô tả cho video "Yoga buổi sáng" thuộc danh mục Thể thao.', 1, 4, 15000, 80),
(N'V029', N'Cầu lông kỹ thuật', N'https://picsum.photos/seed/v29/300/200', 423, N'Mô tả cho video "Cầu lông kỹ thuật" thuộc danh mục Thể thao.', 1, 4, 12000, 70),
(N'V030', N'Bơi lội cơ bản', N'https://picsum.photos/seed/v30/300/200', 560, N'Mô tả cho video "Bơi lội cơ bản" thuộc danh mục Thể thao.', 1, 4, 20000, 50),
(N'V031', N'Tennis trận chung kết', N'https://picsum.photos/seed/v31/300/200', 697, N'Mô tả cho video "Tennis trận chung kết" thuộc danh mục Thể thao.', 1, 4, 25000, 30),
(N'V032', N'Gym cho người mới', N'https://picsum.photos/seed/v32/300/200', 834, N'Mô tả cho video "Gym cho người mới" thuộc danh mục Thể thao.', 1, 4, 30000, 40);

INSERT INTO dbo.Shares (Emails, SharedDate, Username, VideoId) VALUES
(N'user20@mail.com', '2026-09-02', N'user2', N'V001'),
(N'user31@mail.com', '2026-09-03', N'user3', N'V001'),
(N'admin2@mail.com', '2026-09-04', N'admin', N'V001'),
(N'user30@mail.com', '2026-09-03', N'user3', N'V002'),
(N'admin0@mail.com', '2026-09-04', N'admin', N'V003'),
(N'user11@mail.com', '2026-09-05', N'user1', N'V003'),
(N'user22@mail.com', '2026-09-06', N'user2', N'V003'),
(N'user33@mail.com', '2026-09-07', N'user3', N'V003'),
(N'user10@mail.com', '2026-09-05', N'user1', N'V004'),
(N'user21@mail.com', '2026-09-06', N'user2', N'V004'),
(N'user30@mail.com', '2026-09-07', N'user3', N'V006'),
(N'admin1@mail.com', '2026-09-08', N'admin', N'V006'),
(N'user12@mail.com', '2026-09-09', N'user1', N'V006'),
(N'admin0@mail.com', '2026-09-08', N'admin', N'V007'),
(N'user10@mail.com', '2026-09-09', N'user1', N'V008'),
(N'user21@mail.com', '2026-09-10', N'user2', N'V008'),
(N'user32@mail.com', '2026-09-11', N'user3', N'V008'),
(N'admin3@mail.com', '2026-09-12', N'admin', N'V008'),
(N'user20@mail.com', '2026-09-10', N'user2', N'V009'),
(N'user31@mail.com', '2026-09-11', N'user3', N'V009'),
(N'admin0@mail.com', '2026-09-12', N'admin', N'V011'),
(N'user11@mail.com', '2026-09-13', N'user1', N'V011'),
(N'user22@mail.com', '2026-09-14', N'user2', N'V011'),
(N'user10@mail.com', '2026-09-13', N'user1', N'V012'),
(N'user20@mail.com', '2026-09-14', N'user2', N'V013'),
(N'user31@mail.com', '2026-09-15', N'user3', N'V013'),
(N'admin2@mail.com', '2026-09-16', N'admin', N'V013'),
(N'user13@mail.com', '2026-09-17', N'user1', N'V013'),
(N'user30@mail.com', '2026-09-15', N'user3', N'V014'),
(N'admin1@mail.com', '2026-09-16', N'admin', N'V014'),
(N'user10@mail.com', '2026-09-17', N'user1', N'V016'),
(N'user21@mail.com', '2026-09-18', N'user2', N'V016'),
(N'user32@mail.com', '2026-09-19', N'user3', N'V016'),
(N'user20@mail.com', '2026-09-18', N'user2', N'V017'),
(N'user30@mail.com', '2026-09-19', N'user3', N'V018'),
(N'admin1@mail.com', '2026-09-20', N'admin', N'V018'),
(N'user12@mail.com', '2026-09-21', N'user1', N'V018'),
(N'user23@mail.com', '2026-09-22', N'user2', N'V018'),
(N'admin0@mail.com', '2026-09-20', N'admin', N'V019'),
(N'user11@mail.com', '2026-09-21', N'user1', N'V019'),
(N'user20@mail.com', '2026-09-22', N'user2', N'V021'),
(N'user31@mail.com', '2026-09-23', N'user3', N'V021'),
(N'admin2@mail.com', '2026-09-24', N'admin', N'V021'),
(N'user30@mail.com', '2026-09-23', N'user3', N'V022'),
(N'admin0@mail.com', '2026-09-24', N'admin', N'V023'),
(N'user11@mail.com', '2026-09-25', N'user1', N'V023'),
(N'user22@mail.com', '2026-09-26', N'user2', N'V023'),
(N'user33@mail.com', '2026-09-27', N'user3', N'V023'),
(N'user10@mail.com', '2026-09-25', N'user1', N'V024'),
(N'user21@mail.com', '2026-09-26', N'user2', N'V024'),
(N'user30@mail.com', '2026-09-27', N'user3', N'V026'),
(N'admin1@mail.com', '2026-09-28', N'admin', N'V026'),
(N'user12@mail.com', '2026-09-01', N'user1', N'V026'),
(N'admin0@mail.com', '2026-09-28', N'admin', N'V027'),
(N'user10@mail.com', '2026-09-01', N'user1', N'V028'),
(N'user21@mail.com', '2026-09-02', N'user2', N'V028'),
(N'user32@mail.com', '2026-09-03', N'user3', N'V028'),
(N'admin3@mail.com', '2026-09-04', N'admin', N'V028'),
(N'user20@mail.com', '2026-09-02', N'user2', N'V029'),
(N'user31@mail.com', '2026-09-03', N'user3', N'V029'),
(N'admin0@mail.com', '2026-09-04', N'admin', N'V031'),
(N'user11@mail.com', '2026-09-05', N'user1', N'V031'),
(N'user22@mail.com', '2026-09-06', N'user2', N'V031'),
(N'user10@mail.com', '2026-09-05', N'user1', N'V032');

INSERT INTO dbo.Favorites (LikedDate, VideoId, Username) VALUES
('2026-09-03', N'V001', N'user2'),
('2026-09-04', N'V001', N'user3'),
('2026-09-05', N'V001', N'admin'),
('2026-09-06', N'V001', N'user1'),
('2026-09-07', N'V001', N'user2'),
('2026-09-05', N'V002', N'user3'),
('2026-09-06', N'V002', N'admin'),
('2026-09-07', N'V002', N'user1'),
('2026-09-08', N'V002', N'user2'),
('2026-09-07', N'V003', N'admin'),
('2026-09-08', N'V003', N'user1'),
('2026-09-09', N'V003', N'user2'),
('2026-09-09', N'V004', N'user1'),
('2026-09-10', N'V004', N'user2'),
('2026-09-11', N'V005', N'user2'),
('2026-09-15', N'V007', N'admin'),
('2026-09-16', N'V007', N'user1'),
('2026-09-17', N'V007', N'user2'),
('2026-09-18', N'V007', N'user3'),
('2026-09-19', N'V007', N'admin'),
('2026-09-17', N'V008', N'user1'),
('2026-09-18', N'V008', N'user2'),
('2026-09-19', N'V008', N'user3'),
('2026-09-20', N'V008', N'admin'),
('2026-09-19', N'V009', N'user2'),
('2026-09-20', N'V009', N'user3'),
('2026-09-21', N'V009', N'admin'),
('2026-09-21', N'V010', N'user3'),
('2026-09-22', N'V010', N'admin'),
('2026-09-23', N'V011', N'admin'),
('2026-09-27', N'V013', N'user2'),
('2026-09-28', N'V013', N'user3'),
('2026-09-01', N'V013', N'admin'),
('2026-09-02', N'V013', N'user1'),
('2026-09-03', N'V013', N'user2'),
('2026-09-01', N'V014', N'user3'),
('2026-09-02', N'V014', N'admin'),
('2026-09-03', N'V014', N'user1'),
('2026-09-04', N'V014', N'user2'),
('2026-09-03', N'V015', N'admin'),
('2026-09-04', N'V015', N'user1'),
('2026-09-05', N'V015', N'user2'),
('2026-09-05', N'V016', N'user1'),
('2026-09-06', N'V016', N'user2'),
('2026-09-07', N'V017', N'user2'),
('2026-09-11', N'V019', N'admin'),
('2026-09-12', N'V019', N'user1'),
('2026-09-13', N'V019', N'user2'),
('2026-09-14', N'V019', N'user3'),
('2026-09-15', N'V019', N'admin'),
('2026-09-13', N'V020', N'user1'),
('2026-09-14', N'V020', N'user2'),
('2026-09-15', N'V020', N'user3'),
('2026-09-16', N'V020', N'admin'),
('2026-09-15', N'V021', N'user2'),
('2026-09-16', N'V021', N'user3'),
('2026-09-17', N'V021', N'admin'),
('2026-09-17', N'V022', N'user3'),
('2026-09-18', N'V022', N'admin'),
('2026-09-19', N'V023', N'admin'),
('2026-09-23', N'V025', N'user2'),
('2026-09-24', N'V025', N'user3'),
('2026-09-25', N'V025', N'admin'),
('2026-09-26', N'V025', N'user1'),
('2026-09-27', N'V025', N'user2'),
('2026-09-25', N'V026', N'user3'),
('2026-09-26', N'V026', N'admin'),
('2026-09-27', N'V026', N'user1'),
('2026-09-28', N'V026', N'user2'),
('2026-09-27', N'V027', N'admin'),
('2026-09-28', N'V027', N'user1'),
('2026-09-01', N'V027', N'user2'),
('2026-09-01', N'V028', N'user1'),
('2026-09-02', N'V028', N'user2'),
('2026-09-03', N'V029', N'user2'),
('2026-09-07', N'V031', N'admin'),
('2026-09-08', N'V031', N'user1'),
('2026-09-09', N'V031', N'user2'),
('2026-09-10', N'V031', N'user3'),
('2026-09-11', N'V031', N'admin'),
('2026-09-09', N'V032', N'user1'),
('2026-09-10', N'V032', N'user2'),
('2026-09-11', N'V032', N'user3'),
('2026-09-12', N'V032', N'admin');
GO

SELECT c.Categoryname, COUNT(v.VideoId) AS SoVideo FROM dbo.Category c LEFT JOIN dbo.Videos v ON v.CategoryId=c.CategoryId GROUP BY c.Categoryname;
