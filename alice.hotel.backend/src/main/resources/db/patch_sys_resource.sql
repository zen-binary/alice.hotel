-- Align resource tables with the internship permission model, then seed API resources.
-- Safe to re-run: tables are recreated because the old columns (rs_name, no rr_id) cannot serve the API.

DROP TABLE IF EXISTS sys_resource_role;
DROP TABLE IF EXISTS sys_resource;

CREATE TABLE sys_resource (
  rs_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  url VARCHAR(500) NULL,
  http_method VARCHAR(20) NULL,
  rs_nm VARCHAR(200) NULL,
  rs_type VARCHAR(50) NULL,
  reg_date DATETIME NULL
);

CREATE TABLE sys_resource_role (
  rr_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  rs_id BIGINT NOT NULL,
  rl_id BIGINT NOT NULL
);

INSERT INTO sys_resource (rs_id, url, http_method, rs_nm, rs_type, reg_date) VALUES
  (1,  '/a/hotel/**',            'ALL', 'Hồ sơ khách sạn',     'API', NOW()),
  (2,  '/a/room-types/**',       'ALL', 'Loại phòng',           'API', NOW()),
  (3,  '/a/rooms/**',            'ALL', 'Phòng',                'API', NOW()),
  (4,  '/a/hotel-services/**',   'ALL', 'Dịch vụ',              'API', NOW()),
  (5,  '/a/vouchers/**',         'ALL', 'Mã giảm giá',         'API', NOW()),
  (6,  '/a/bookings/**',         'ALL', 'Đặt phòng',            'API', NOW()),
  (7,  '/a/front-desk/**',       'ALL', 'Lễ tân',               'API', NOW()),
  (8,  '/a/rates/**',            'ALL', 'Bảng giá',             'API', NOW()),
  (9,  '/a/shifts/**',           'ALL', 'Ca trực',              'API', NOW()),
  (10, '/a/dashboard/**',        'ALL', 'Báo cáo vận hành',     'API', NOW()),
  (11, '/a/reports/**',          'ALL', 'Xuất Excel',           'API', NOW()),
  (12, '/a/guests/**',           'ALL', 'Tài khoản khách',      'API', NOW()),
  (13, '/a/reviews/**',          'ALL', 'Đánh giá',             'API', NOW()),
  (14, '/a/menu/**',             'ALL', 'Quản lý menu',         'API', NOW()),
  (15, '/a/resources/**',        'ALL', 'Tài nguyên API',       'API', NOW()),
  (16, '/a/resources-roles/**',  'ALL', 'Gán vai trò cho API',  'API', NOW()),
  (17, '/a/roles/**',            'ALL', 'Danh sách vai trò',    'API', NOW()),
  (18, '/a/role/**',             'ALL', 'Chi tiết vai trò',     'API', NOW()),
  (19, '/c/booking/mine',        'GET', 'Đơn của khách',        'API', NOW()),
  (20, '/c/booking/reviews',     'POST','Khách gửi đánh giá',   'API', NOW());

-- Role 1 quản trị: toàn bộ API admin. Role 3 lễ tân: vận hành. Role 2 khách: API site khách.
INSERT INTO sys_resource_role (rs_id, rl_id) VALUES
  (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),
  (2,3),(3,3),(4,3),(6,3),(7,3),(9,3),(10,3),(12,3),(13,3),
  (19,2),(20,2);

ALTER TABLE sys_resource AUTO_INCREMENT = 21;
