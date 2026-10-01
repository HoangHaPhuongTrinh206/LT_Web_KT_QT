-- Cập nhật giá và số lượng cho các video đang có Price/Quantity là NULL hoặc 0
USE De03_24162131;
GO

UPDATE dbo.Videos SET Price = 10000, Quantity = 50 WHERE VideoId = 'V001';
UPDATE dbo.Videos SET Price = 15000, Quantity = 30 WHERE VideoId = 'V002';
UPDATE dbo.Videos SET Price = 20000, Quantity = 20 WHERE VideoId = 'V003';
UPDATE dbo.Videos SET Price = 12000, Quantity = 40 WHERE VideoId = 'V004';
UPDATE dbo.Videos SET Price = 25000, Quantity = 15 WHERE VideoId = 'V005';
UPDATE dbo.Videos SET Price = 18000, Quantity = 25 WHERE VideoId = 'V006';
UPDATE dbo.Videos SET Price = 15000, Quantity = 30 WHERE VideoId = 'V007';
UPDATE dbo.Videos SET Price = 20000, Quantity = 10 WHERE VideoId = 'V008';
UPDATE dbo.Videos SET Price = 50000, Quantity = 100 WHERE VideoId = 'V009';
UPDATE dbo.Videos SET Price = 60000, Quantity = 100 WHERE VideoId = 'V010';
UPDATE dbo.Videos SET Price = 55000, Quantity = 80  WHERE VideoId = 'V011';
UPDATE dbo.Videos SET Price = 70000, Quantity = 50  WHERE VideoId = 'V012';
UPDATE dbo.Videos SET Price = 40000, Quantity = 200 WHERE VideoId = 'V013';
UPDATE dbo.Videos SET Price = 35000, Quantity = 150 WHERE VideoId = 'V014';
UPDATE dbo.Videos SET Price = 30000, Quantity = 100 WHERE VideoId = 'V015';
UPDATE dbo.Videos SET Price = 45000, Quantity = 60  WHERE VideoId = 'V016';
UPDATE dbo.Videos SET Price = 20000, Quantity = 40  WHERE VideoId = 'V017';
UPDATE dbo.Videos SET Price = 15000, Quantity = 50  WHERE VideoId = 'V018';
UPDATE dbo.Videos SET Price = 25000, Quantity = 30  WHERE VideoId = 'V019';
UPDATE dbo.Videos SET Price = 10000, Quantity = 100 WHERE VideoId = 'V020';
UPDATE dbo.Videos SET Price = 15000, Quantity = 80  WHERE VideoId = 'V021';
UPDATE dbo.Videos SET Price = 18000, Quantity = 50  WHERE VideoId = 'V022';
UPDATE dbo.Videos SET Price = 20000, Quantity = 40  WHERE VideoId = 'V023';
UPDATE dbo.Videos SET Price =  5000, Quantity = 200 WHERE VideoId = 'V024';
UPDATE dbo.Videos SET Price = 15000, Quantity = 60  WHERE VideoId = 'V025';
UPDATE dbo.Videos SET Price = 20000, Quantity = 40  WHERE VideoId = 'V026';
UPDATE dbo.Videos SET Price = 10000, Quantity = 100 WHERE VideoId = 'V027';
UPDATE dbo.Videos SET Price = 15000, Quantity = 80  WHERE VideoId = 'V028';
UPDATE dbo.Videos SET Price = 12000, Quantity = 70  WHERE VideoId = 'V029';
UPDATE dbo.Videos SET Price = 20000, Quantity = 50  WHERE VideoId = 'V030';
UPDATE dbo.Videos SET Price = 25000, Quantity = 30  WHERE VideoId = 'V031';
UPDATE dbo.Videos SET Price = 30000, Quantity = 40  WHERE VideoId = 'V032';
GO

-- Kiểm tra lại
SELECT VideoId, Title, Price, Quantity FROM dbo.Videos ORDER BY VideoId;
GO
