-- Alice Hotel — Phase 0+ booking domain schema (run after alice_hotel_init.sql)
USE alice_hotel;

CREATE TABLE IF NOT EXISTS ah_hotel (
  hotel_id VARCHAR(40) NOT NULL PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  address VARCHAR(500) NULL,
  phone VARCHAR(50) NULL,
  email VARCHAR(100) NULL,
  description VARCHAR(2000) NULL,
  check_in_time VARCHAR(10) NOT NULL DEFAULT '14:00',
  check_out_time VARCHAR(10) NOT NULL DEFAULT '12:00',
  cancel_free_hours INT NOT NULL DEFAULT 48,
  cancel_refund_percent INT NOT NULL DEFAULT 100,
  deposit_percent INT NOT NULL DEFAULT 30,
  early_checkin_fee DECIMAL(12,2) NOT NULL DEFAULT 0,
  late_checkout_fee DECIMAL(12,2) NOT NULL DEFAULT 0,
  hold_minutes INT NOT NULL DEFAULT 15,
  image_url VARCHAR(500) NULL,
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  reg_id VARCHAR(50) NULL,
  reg_date DATETIME NULL,
  mod_id VARCHAR(50) NULL,
  mod_date DATETIME NULL
);

CREATE TABLE IF NOT EXISTS ah_amenity (
  amenity_id VARCHAR(40) NOT NULL PRIMARY KEY,
  code VARCHAR(50) NOT NULL,
  name VARCHAR(200) NOT NULL,
  icon VARCHAR(100) NULL,
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  UNIQUE KEY uk_amenity_code (code)
);

CREATE TABLE IF NOT EXISTS ah_room_type (
  room_type_id VARCHAR(40) NOT NULL PRIMARY KEY,
  code VARCHAR(50) NOT NULL,
  name VARCHAR(200) NOT NULL,
  description VARCHAR(2000) NULL,
  base_price DECIMAL(12,2) NOT NULL DEFAULT 0,
  capacity_adult INT NOT NULL DEFAULT 2,
  capacity_child INT NOT NULL DEFAULT 0,
  bed_info VARCHAR(200) NULL,
  image_urls VARCHAR(2000) NULL,
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  reg_id VARCHAR(50) NULL,
  reg_date DATETIME NULL,
  mod_id VARCHAR(50) NULL,
  mod_date DATETIME NULL,
  UNIQUE KEY uk_room_type_code (code)
);

CREATE TABLE IF NOT EXISTS ah_room_type_amenity (
  room_type_id VARCHAR(40) NOT NULL,
  amenity_id VARCHAR(40) NOT NULL,
  PRIMARY KEY (room_type_id, amenity_id)
);

CREATE TABLE IF NOT EXISTS ah_room (
  room_id VARCHAR(40) NOT NULL PRIMARY KEY,
  room_type_id VARCHAR(40) NOT NULL,
  room_number VARCHAR(50) NOT NULL,
  floor_no INT NULL,
  house_status VARCHAR(30) NOT NULL DEFAULT 'VACANT_CLEAN',
  active_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
  note VARCHAR(500) NULL,
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  reg_id VARCHAR(50) NULL,
  reg_date DATETIME NULL,
  mod_id VARCHAR(50) NULL,
  mod_date DATETIME NULL,
  UNIQUE KEY uk_room_number (room_number)
);

CREATE TABLE IF NOT EXISTS ah_rate (
  rate_id VARCHAR(40) NOT NULL PRIMARY KEY,
  room_type_id VARCHAR(40) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  dow_mask VARCHAR(20) NULL COMMENT '0123456 Sun-Sat, null=all',
  stop_sell_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  name VARCHAR(200) NULL,
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N'
);

CREATE TABLE IF NOT EXISTS ah_service (
  service_id VARCHAR(40) NOT NULL PRIMARY KEY,
  code VARCHAR(50) NOT NULL,
  name VARCHAR(200) NOT NULL,
  category VARCHAR(50) NULL,
  unit_price DECIMAL(12,2) NOT NULL DEFAULT 0,
  unit_name VARCHAR(50) NULL,
  active_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  UNIQUE KEY uk_service_code (code)
);

CREATE TABLE IF NOT EXISTS ah_voucher (
  voucher_id VARCHAR(40) NOT NULL PRIMARY KEY,
  code VARCHAR(50) NOT NULL,
  name VARCHAR(200) NOT NULL,
  discount_type VARCHAR(20) NOT NULL COMMENT 'PERCENT|AMOUNT',
  discount_value DECIMAL(12,2) NOT NULL,
  max_discount DECIMAL(12,2) NULL,
  min_spend DECIMAL(12,2) NULL,
  start_date DATE NULL,
  end_date DATE NULL,
  usage_limit INT NULL,
  used_count INT NOT NULL DEFAULT 0,
  active_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  UNIQUE KEY uk_voucher_code (code)
);

CREATE TABLE IF NOT EXISTS ah_guest_profile (
  user_id VARCHAR(50) NOT NULL PRIMARY KEY,
  email VARCHAR(100) NULL,
  phone VARCHAR(50) NULL,
  id_number VARCHAR(50) NULL,
  address VARCHAR(500) NULL,
  preference_json VARCHAR(1000) NULL,
  locked_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  internal_note VARCHAR(1000) NULL,
  reg_date DATETIME NULL,
  mod_date DATETIME NULL
);

CREATE TABLE IF NOT EXISTS ah_otp (
  otp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(100) NOT NULL,
  otp_code VARCHAR(10) NOT NULL,
  purpose VARCHAR(30) NOT NULL,
  expire_at DATETIME NOT NULL,
  used_yn VARCHAR(1) NOT NULL DEFAULT 'N'
);

CREATE TABLE IF NOT EXISTS ah_booking (
  booking_id VARCHAR(40) NOT NULL PRIMARY KEY,
  booking_code VARCHAR(30) NOT NULL,
  user_id VARCHAR(50) NULL,
  guest_name VARCHAR(200) NOT NULL,
  guest_phone VARCHAR(50) NULL,
  guest_email VARCHAR(100) NULL,
  guest_id_number VARCHAR(50) NULL,
  room_type_id VARCHAR(40) NOT NULL,
  room_id VARCHAR(40) NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  adults INT NOT NULL DEFAULT 1,
  children INT NOT NULL DEFAULT 0,
  status VARCHAR(30) NOT NULL,
  source VARCHAR(30) NOT NULL DEFAULT 'ONLINE',
  hold_expire_at DATETIME NULL,
  room_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  service_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  discount_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  fee_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  total_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  paid_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
  voucher_id VARCHAR(40) NULL,
  cancel_reason VARCHAR(500) NULL,
  note VARCHAR(1000) NULL,
  reminder_sent_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  reg_id VARCHAR(50) NULL,
  reg_date DATETIME NULL,
  mod_id VARCHAR(50) NULL,
  mod_date DATETIME NULL,
  UNIQUE KEY uk_booking_code (booking_code)
);

CREATE TABLE IF NOT EXISTS ah_booking_service (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  booking_id VARCHAR(40) NOT NULL,
  service_id VARCHAR(40) NOT NULL,
  qty INT NOT NULL DEFAULT 1,
  unit_price DECIMAL(12,2) NOT NULL,
  amount DECIMAL(12,2) NOT NULL,
  note VARCHAR(500) NULL
);

CREATE TABLE IF NOT EXISTS ah_payment (
  payment_id VARCHAR(40) NOT NULL PRIMARY KEY,
  booking_id VARCHAR(40) NOT NULL,
  method VARCHAR(30) NOT NULL COMMENT 'VNPAY|CASH|TRANSFER',
  amount DECIMAL(12,2) NOT NULL,
  status VARCHAR(30) NOT NULL,
  txn_ref VARCHAR(100) NULL,
  vnp_txn_no VARCHAR(100) NULL,
  shift_id VARCHAR(40) NULL,
  note VARCHAR(500) NULL,
  reg_id VARCHAR(50) NULL,
  reg_date DATETIME NULL
);

CREATE TABLE IF NOT EXISTS ah_review (
  review_id VARCHAR(40) NOT NULL PRIMARY KEY,
  booking_id VARCHAR(40) NOT NULL,
  user_id VARCHAR(50) NOT NULL,
  room_type_id VARCHAR(40) NOT NULL,
  rating INT NOT NULL,
  comment VARCHAR(2000) NULL,
  admin_reply VARCHAR(2000) NULL,
  del_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  reg_date DATETIME NULL,
  UNIQUE KEY uk_review_booking (booking_id)
);

CREATE TABLE IF NOT EXISTS ah_shift (
  shift_id VARCHAR(40) NOT NULL PRIMARY KEY,
  user_id VARCHAR(50) NOT NULL,
  opened_at DATETIME NOT NULL,
  closed_at DATETIME NULL,
  opening_cash DECIMAL(12,2) NOT NULL DEFAULT 0,
  closing_cash DECIMAL(12,2) NULL,
  expected_cash DECIMAL(12,2) NULL,
  cash_total DECIMAL(12,2) NULL,
  transfer_total DECIMAL(12,2) NULL,
  online_total DECIMAL(12,2) NULL,
  note VARCHAR(1000) NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'OPEN'
);

CREATE TABLE IF NOT EXISTS ah_audit_log (
  log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  actor_id VARCHAR(50) NULL,
  action VARCHAR(100) NOT NULL,
  entity_type VARCHAR(50) NULL,
  entity_id VARCHAR(50) NULL,
  detail VARCHAR(2000) NULL,
  reg_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Seed hotel + master data
INSERT INTO ah_hotel (hotel_id, name, address, phone, email, description, check_in_time, check_out_time,
  cancel_free_hours, cancel_refund_percent, deposit_percent, early_checkin_fee, late_checkout_fee, hold_minutes, del_yn, reg_id, reg_date)
VALUES ('HOTEL001', 'Alice Hotel', '28 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', '024 3933 2468', 'contact@alicehotel.vn',
  'Alice Hotel là khách sạn thành phố tại Hoàn Kiếm, phục vụ khách công tác và nghỉ ngắn ngày. Giá phòng được niêm yết theo đêm, lễ tân nhận phòng từ 14:00 và hỗ trợ đặt cọc online hoặc thanh toán tại quầy.',
  '14:00', '12:00', 48, 100, 30, 200000, 200000, 15, 'N', 'admin', NOW())
ON DUPLICATE KEY UPDATE
  address = VALUES(address), phone = VALUES(phone), email = VALUES(email), description = VALUES(description);

INSERT INTO ah_amenity (amenity_id, code, name, icon, del_yn) VALUES
 ('AM01', 'WIFI', 'Wi-Fi', 'wifi', 'N'),
 ('AM02', 'AC', 'Điều hòa', 'ac', 'N'),
 ('AM03', 'POOL', 'Hồ bơi', 'pool', 'N'),
 ('AM04', 'PARKING', 'Chỗ đỗ xe', 'parking', 'N')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO ah_room_type (room_type_id, code, name, description, base_price, capacity_adult, capacity_child, bed_info, del_yn, reg_id, reg_date) VALUES
 ('RT01', 'STANDARD', 'Tiêu chuẩn', 'Phòng tiêu chuẩn cho hai khách, hai giường đơn, bàn làm việc và cửa sổ phố. Phù hợp công tác một đến hai đêm.', 800000, 2, 1, '2 giường đơn', 'N', 'admin', NOW()),
 ('RT02', 'DELUXE', 'Cao cấp', 'Phòng cao cấp hướng phố, giường lớn, ban công nhỏ và chỗ ngồi đọc sách. Dành cho khách muốn không gian yên hơn.', 1200000, 2, 1, '1 giường lớn', 'N', 'admin', NOW()),
 ('RT03', 'SUITE', 'Hạng sang', 'Phòng hạng sang có phòng khách riêng, giường lớn và ghế dài. Phù hợp ở dài ngày hoặc tiếp khách ngắn trong ngày.', 2000000, 3, 2, '1 giường lớn và ghế dài', 'N', 'admin', NOW()),
 ('RT04', 'FAMILY', 'Gia đình', 'Phòng gia đình với hai giường đôi lớn, khu vực ngồi chung. Sức chứa bốn người lớn và hai trẻ em.', 1800000, 4, 2, '2 giường đôi lớn', 'N', 'admin', NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT IGNORE INTO ah_room_type_amenity (room_type_id, amenity_id) VALUES
 ('RT01','AM01'),('RT01','AM02'),
 ('RT02','AM01'),('RT02','AM02'),('RT02','AM04'),
 ('RT03','AM01'),('RT03','AM02'),('RT03','AM03'),('RT03','AM04'),
 ('RT04','AM01'),('RT04','AM02'),('RT04','AM04');

INSERT INTO ah_room (room_id, room_type_id, room_number, floor_no, house_status, active_yn, del_yn, reg_id, reg_date) VALUES
 ('R101','RT01','101',1,'VACANT_CLEAN','Y','N','admin',NOW()),
 ('R102','RT01','102',1,'VACANT_CLEAN','Y','N','admin',NOW()),
 ('R201','RT02','201',2,'VACANT_CLEAN','Y','N','admin',NOW()),
 ('R202','RT02','202',2,'VACANT_CLEAN','Y','N','admin',NOW()),
 ('R301','RT03','301',3,'VACANT_CLEAN','Y','N','admin',NOW()),
 ('R401','RT04','401',4,'VACANT_CLEAN','Y','N','admin',NOW())
ON DUPLICATE KEY UPDATE room_type_id = VALUES(room_type_id);

INSERT INTO ah_service (service_id, code, name, category, unit_price, unit_name, active_yn, del_yn) VALUES
 ('SV01','BREAKFAST','Bữa sáng','FNB',150000,'suất','Y','N'),
 ('SV02','EXTRA_BED','Giường phụ','ROOM',200000,'giường','Y','N'),
 ('SV03','AIRPORT','Đưa đón sân bay','TRANSPORT',350000,'lượt','Y','N'),
 ('SV04','LAUNDRY','Giặt ủi','OTHER',80000,'kg','Y','N'),
 ('SV05','MINIBAR','Minibar','FNB',100000,'lần','Y','N')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO ah_voucher (voucher_id, code, name, discount_type, discount_value, max_discount, min_spend,
  start_date, end_date, usage_limit, used_count, active_yn, del_yn) VALUES
 ('VC01','ALICE10','Giảm 10% kỳ ở', 'PERCENT', 10, 500000, 1000000, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 365 DAY), 100, 0, 'Y', 'N'),
 ('VC02','WELCOME200','Giảm 200.000đ chào mừng', 'AMOUNT', 200000, NULL, 500000, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 365 DAY), 50, 0, 'Y', 'N')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Guest demo user (password Alice@123 same bcrypt as admin)
INSERT INTO CPS_USER (USER_ID, USER_PW, USER_NO, USER_NAME, USER_DIV, DIVISION)
VALUES ('guest', '$2b$10$mmuHRyATxWsB99ovbpJ0HuRBgUtSB1B5Enb/qpz2Y0CcGBnaC55Te', 'G001', 'Nguyễn An Khôi', 'GUEST', 'CLIENT')
ON DUPLICATE KEY UPDATE USER_NAME = VALUES(USER_NAME);

INSERT INTO ah_guest_profile (user_id, email, phone, locked_yn, reg_date)
VALUES ('guest', 'guest@alice.hotel', '0900000001', 'N', NOW())
ON DUPLICATE KEY UPDATE email = VALUES(email);

INSERT INTO SYS_USER_ROLE (RL_ID, USER_ID, USE_YN, PUBLIC_INFO)
SELECT '2', 'guest', 'Y', 'Y'
WHERE NOT EXISTS (
  SELECT 1 FROM SYS_USER_ROLE WHERE USER_ID = 'guest' AND RL_ID = '2'
);

INSERT INTO MENU (ID, SITE_TYPE, NAME, ROUTE, PARENT_ID, DISPLAY_ORDER, DELETE_FLAG, VN_NAME, SCREEN_NAME, BLANK_SCREEN)
VALUES
  (20, 'A', 'Vận hành', NULL, NULL, '3', 'N', 'Vận hành', NULL, NULL),
  (21, 'A', 'Kho phòng', NULL, NULL, '4', 'N', 'Kho phòng', NULL, NULL),
  (22, 'A', 'Bán hàng', NULL, NULL, '5', 'N', 'Bán hàng', NULL, NULL),
  (23, 'A', 'Báo cáo', NULL, NULL, '6', 'N', 'Báo cáo', NULL, NULL),
  (24, 'A', 'Cấu hình', NULL, NULL, '7', 'N', 'Cấu hình', NULL, NULL),
  (14, 'A', 'Lễ tân', '/front-desk', 20, '1', 'N', 'Lễ tân', 'frontDesk', NULL),
  (15, 'A', 'Đặt phòng', '/bookings', 20, '2', 'N', 'Đặt phòng', 'bookingAdmin', NULL),
  (10, 'A', 'Loại phòng', '/room-types', 21, '1', 'N', 'Loại phòng', 'roomType', NULL),
  (11, 'A', 'Phòng', '/rooms', 21, '2', 'N', 'Phòng', 'room', NULL),
  (18, 'A', 'Bảng giá', '/rates', 21, '3', 'N', 'Bảng giá', 'rateCalendar', NULL),
  (12, 'A', 'Dịch vụ', '/hotel-services', 22, '1', 'N', 'Dịch vụ', 'hotelService', NULL),
  (13, 'A', 'Mã giảm giá', '/vouchers', 22, '2', 'N', 'Mã giảm giá', 'voucher', NULL),
  (16, 'A', 'Dashboard', '/dashboard', 23, '1', 'N', 'Dashboard', 'dashboard', NULL),
  (17, 'A', 'Khách sạn', '/hotel-settings', 24, '1', 'N', 'Khách sạn', 'hotelSettings', NULL)
ON DUPLICATE KEY UPDATE NAME = VALUES(NAME), ROUTE = VALUES(ROUTE), PARENT_ID = VALUES(PARENT_ID),
  VN_NAME = VALUES(VN_NAME), SCREEN_NAME = VALUES(SCREEN_NAME), DISPLAY_ORDER = VALUES(DISPLAY_ORDER), DELETE_FLAG = 'N';

INSERT IGNORE INTO MENU_ROLE (MENU_ID, ROLE_ID)
VALUES (10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(20,1),(21,1),(22,1),(23,1),(24,1);
