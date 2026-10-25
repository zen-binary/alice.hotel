-- Fill demo content that was still placeholder: hotel profile, rooms, rates, guests, bookings.
-- Safe to re-run.

UPDATE ah_hotel
SET
  address = '28 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội',
  phone = '024 3933 2468',
  email = 'contact@alicehotel.vn',
  description = 'Alice Hotel là khách sạn thành phố tại Hoàn Kiếm, phục vụ khách công tác và nghỉ ngắn ngày. Giá phòng được niêm yết theo đêm, lễ tân nhận phòng từ 14:00 và hỗ trợ đặt cọc online hoặc thanh toán tại quầy.',
  image_url = 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1600&q=80',
  mod_id = 'admin',
  mod_date = NOW()
WHERE hotel_id = 'HOTEL001';

UPDATE ah_amenity SET name = 'Wi-Fi' WHERE amenity_id = 'AM01';
UPDATE ah_amenity SET name = 'Điều hòa' WHERE amenity_id = 'AM02';
UPDATE ah_amenity SET name = 'Hồ bơi' WHERE amenity_id = 'AM03';
UPDATE ah_amenity SET name = 'Chỗ đỗ xe' WHERE amenity_id = 'AM04';

UPDATE ah_room_type
SET name = 'Tiêu chuẩn',
    description = 'Phòng tiêu chuẩn cho hai khách, hai giường đơn, bàn làm việc và cửa sổ phố. Phù hợp công tác một đến hai đêm.',
    bed_info = '2 giường đơn',
    image_urls = 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=1200&q=80'
WHERE room_type_id = 'RT01';

UPDATE ah_room_type
SET name = 'Cao cấp',
    description = 'Phòng cao cấp hướng phố, giường lớn, ban công nhỏ và chỗ ngồi đọc sách. Dành cho khách muốn không gian yên hơn.',
    bed_info = '1 giường lớn',
    image_urls = 'https://images.unsplash.com/photo-1611892440504-42a792e24d32?auto=format&fit=crop&w=1200&q=80'
WHERE room_type_id = 'RT02';

UPDATE ah_room_type
SET name = 'Hạng sang',
    description = 'Phòng hạng sang có phòng khách riêng, giường lớn và ghế dài. Phù hợp ở dài ngày hoặc tiếp khách ngắn trong ngày.',
    bed_info = '1 giường lớn và ghế dài',
    image_urls = 'https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?auto=format&fit=crop&w=1200&q=80'
WHERE room_type_id = 'RT03';

UPDATE ah_room_type
SET name = 'Gia đình',
    description = 'Phòng gia đình với hai giường đôi lớn, khu vực ngồi chung. Sức chứa bốn người lớn và hai trẻ em.',
    bed_info = '2 giường đôi lớn',
    image_urls = 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?auto=format&fit=crop&w=1200&q=80'
WHERE room_type_id = 'RT04';

UPDATE ah_service SET name = 'Bữa sáng', unit_name = 'suất' WHERE service_id = 'SV01';
UPDATE ah_service SET name = 'Giường phụ', unit_name = 'giường' WHERE service_id = 'SV02';
UPDATE ah_service SET name = 'Đưa đón sân bay', unit_name = 'lượt' WHERE service_id = 'SV03';
UPDATE ah_service SET name = 'Giặt ủi', unit_name = 'kg' WHERE service_id = 'SV04';
UPDATE ah_service SET name = 'Minibar', unit_name = 'lần' WHERE service_id = 'SV05';

UPDATE ah_voucher SET name = 'Giảm 10% kỳ ở' WHERE voucher_id = 'VC01';
UPDATE ah_voucher SET name = 'Giảm 200.000đ chào mừng' WHERE voucher_id = 'VC02';

INSERT INTO ah_room (room_id, room_type_id, room_number, floor_no, house_status, active_yn, del_yn, note, reg_id, reg_date)
VALUES
  ('R103', 'RT01', '103', 1, 'VACANT_DIRTY', 'Y', 'N', 'Khách vừa trả, chờ dọn', 'admin', NOW()),
  ('R104', 'RT01', '104', 1, 'VACANT_CLEAN', 'Y', 'N', NULL, 'admin', NOW()),
  ('R203', 'RT02', '203', 2, 'VACANT_CLEAN', 'Y', 'N', NULL, 'admin', NOW()),
  ('R204', 'RT02', '204', 2, 'OUT_OF_ORDER', 'Y', 'N', 'Bảo trì điều hòa', 'admin', NOW()),
  ('R302', 'RT03', '302', 3, 'VACANT_CLEAN', 'Y', 'N', NULL, 'admin', NOW()),
  ('R402', 'RT04', '402', 4, 'VACANT_CLEAN', 'Y', 'N', NULL, 'admin', NOW())
ON DUPLICATE KEY UPDATE house_status = VALUES(house_status), note = VALUES(note);

UPDATE ah_room SET house_status = 'OCCUPIED', note = 'Khách đang ở' WHERE room_id = 'R201';
UPDATE ah_room SET house_status = 'OCCUPIED', note = 'Trả phòng hôm nay' WHERE room_id = 'R102';

INSERT INTO ah_rate (rate_id, room_type_id, start_date, end_date, price, dow_mask, stop_sell_yn, name, del_yn)
VALUES
  ('RATE01', 'RT01', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_ADD(CURDATE(), INTERVAL 365 DAY), 800000, NULL, 'N', 'Giá niêm yết tiêu chuẩn', 'N'),
  ('RATE02', 'RT02', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_ADD(CURDATE(), INTERVAL 365 DAY), 1200000, NULL, 'N', 'Giá niêm yết cao cấp', 'N'),
  ('RATE03', 'RT03', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_ADD(CURDATE(), INTERVAL 365 DAY), 2000000, NULL, 'N', 'Giá niêm yết hạng sang', 'N'),
  ('RATE04', 'RT04', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_ADD(CURDATE(), INTERVAL 365 DAY), 1800000, NULL, 'N', 'Giá niêm yết gia đình', 'N')
ON DUPLICATE KEY UPDATE
  start_date = VALUES(start_date),
  end_date = VALUES(end_date),
  price = VALUES(price),
  name = VALUES(name),
  del_yn = 'N';

UPDATE CPS_USER
SET USER_NAME = 'Nguyễn An Khôi'
WHERE USER_ID = 'guest';

UPDATE ah_guest_profile
SET email = 'guest@alice.hotel',
    phone = '0901234567',
    id_number = '001234567890',
    address = '15 Ngõ 4, Phố Huế, Hai Bà Trưng, Hà Nội',
    preference_json = 'Tầng cao, giường lớn, không hút thuốc',
    internal_note = 'Khách đặt lại nhiều lần. Gọi trước 30 phút khi xe sân bay đến.',
    locked_yn = 'N'
WHERE user_id = 'guest';

INSERT INTO CPS_USER (USER_ID, USER_PW, USER_NO, USER_NAME, USER_DIV, DIVISION)
VALUES
  ('thuha', '$2b$10$mmuHRyATxWsB99ovbpJ0HuRBgUtSB1B5Enb/qpz2Y0CcGBnaC55Te', 'G002', 'Nguyễn Thu Hà', 'GUEST', 'CLIENT'),
  ('quochuy', '$2b$10$mmuHRyATxWsB99ovbpJ0HuRBgUtSB1B5Enb/qpz2Y0CcGBnaC55Te', 'G003', 'Trần Quốc Huy', 'GUEST', 'CLIENT'),
  ('ngocmai', '$2b$10$mmuHRyATxWsB99ovbpJ0HuRBgUtSB1B5Enb/qpz2Y0CcGBnaC55Te', 'G004', 'Lê Ngọc Mai', 'GUEST', 'CLIENT')
ON DUPLICATE KEY UPDATE USER_NAME = VALUES(USER_NAME);

INSERT INTO ah_guest_profile (user_id, email, phone, id_number, address, preference_json, locked_yn, internal_note, reg_date)
VALUES
  ('thuha', 'thuha@example.com', '0912345678', '001245678901', '42 Trần Phú, Ba Đình, Hà Nội', 'Gối thêm', 'N', 'Đến công tác, xuất hóa đơn công ty.', DATE_SUB(NOW(), INTERVAL 12 DAY)),
  ('quochuy', 'quochuy@example.com', '0988123456', '079123456789', '8 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 'Phòng yên tĩnh', 'N', 'Đang ở phòng cao cấp 201.', DATE_SUB(NOW(), INTERVAL 4 DAY)),
  ('ngocmai', 'ngocmai@example.com', '0909988776', '031998877665', '19 Lê Lợi, Hải Châu, Đà Nẵng', 'Trả phòng muộn nếu còn phòng', 'N', 'Trả phòng hôm nay.', DATE_SUB(NOW(), INTERVAL 6 DAY))
ON DUPLICATE KEY UPDATE
  email = VALUES(email),
  phone = VALUES(phone),
  id_number = VALUES(id_number),
  address = VALUES(address),
  preference_json = VALUES(preference_json),
  internal_note = VALUES(internal_note);

INSERT INTO SYS_USER_ROLE (RL_ID, USER_ID, USE_YN, PUBLIC_INFO)
SELECT '2', u.USER_ID, 'Y', 'Y'
FROM CPS_USER u
WHERE u.USER_ID IN ('thuha', 'quochuy', 'ngocmai')
  AND NOT EXISTS (
    SELECT 1 FROM SYS_USER_ROLE r WHERE r.USER_ID = u.USER_ID AND r.RL_ID = '2'
  );

INSERT INTO ah_booking (
  booking_id, booking_code, user_id, guest_name, guest_phone, guest_email, guest_id_number,
  room_type_id, room_id, check_in, check_out, adults, children, status, source,
  room_amount, service_amount, discount_amount, fee_amount, total_amount, paid_amount,
  note, reminder_sent_yn, del_yn, reg_id, reg_date
) VALUES
  ('BKDEMO01', 'AL2601', 'thuha', 'Nguyễn Thu Hà', '0912345678', 'thuha@example.com', '001245678901',
   'RT01', 'R101', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 2 DAY), 2, 0, 'CONFIRMED', 'ONLINE',
   1600000, 0, 0, 0, 1600000, 480000,
   'Đến sau 18:00, cần hóa đơn.', 'N', 'N', 'thuha', NOW()),
  ('BKDEMO02', 'AL2602', 'quochuy', 'Trần Quốc Huy', '0988123456', 'quochuy@example.com', '079123456789',
   'RT02', 'R201', DATE_SUB(CURDATE(), INTERVAL 1 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY), 2, 0, 'CHECKED_IN', 'ONLINE',
   3600000, 300000, 0, 0, 3900000, 1170000,
   'Đã nhận phòng tầng 2.', 'Y', 'N', 'letan', DATE_SUB(NOW(), INTERVAL 1 DAY)),
  ('BKDEMO03', 'AL2603', 'ngocmai', 'Lê Ngọc Mai', '0909988776', 'ngocmai@example.com', '031998877665',
   'RT01', 'R102', DATE_SUB(CURDATE(), INTERVAL 2 DAY), CURDATE(), 1, 0, 'CHECKED_IN', 'WALKIN',
   1600000, 0, 0, 0, 1600000, 1600000,
   'Trả phòng trước 12:00.', 'Y', 'N', 'letan', DATE_SUB(NOW(), INTERVAL 2 DAY)),
  ('BKDEMO04', 'AL2604', 'guest', 'Nguyễn An Khôi', '0901234567', 'guest@alice.hotel', '001234567890',
   'RT02', 'R202', DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(CURDATE(), INTERVAL 12 DAY), 2, 0, 'CHECKED_OUT', 'ONLINE',
   2400000, 150000, 0, 0, 2550000, 2550000,
   'Đã trả phòng và đánh giá.', 'Y', 'N', 'guest', DATE_SUB(NOW(), INTERVAL 14 DAY)),
  ('BKDEMO05', 'AL2605', 'guest', 'Nguyễn An Khôi', '0901234567', 'guest@alice.hotel', '001234567890',
   'RT03', 'R301', DATE_ADD(CURDATE(), INTERVAL 7 DAY), DATE_ADD(CURDATE(), INTERVAL 9 DAY), 2, 1, 'CONFIRMED', 'ONLINE',
   4000000, 0, 0, 0, 4000000, 1200000,
   'Kỳ nghỉ cuối tuần, có một trẻ em.', 'N', 'N', 'guest', DATE_SUB(NOW(), INTERVAL 2 DAY)),
  ('BKDEMO06', 'AL2606', 'thuha', 'Nguyễn Thu Hà', '0912345678', 'thuha@example.com', '001245678901',
   'RT04', NULL, DATE_ADD(CURDATE(), INTERVAL 12 DAY), DATE_ADD(CURDATE(), INTERVAL 14 DAY), 3, 1, 'PENDING_PAYMENT', 'ONLINE',
   3600000, 0, 0, 0, 3600000, 0,
   'Chờ cọc, chưa gán phòng.', 'N', 'N', 'thuha', NOW()),
  ('BKDEMO07', 'AL2607', 'quochuy', 'Trần Quốc Huy', '0988123456', 'quochuy@example.com', '079123456789',
   'RT01', NULL, DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, 0, 'CANCELLED', 'ONLINE',
   1600000, 0, 0, 0, 1600000, 0,
   'Khách đổi lịch.', 'N', 'N', 'quochuy', DATE_SUB(NOW(), INTERVAL 8 DAY))
ON DUPLICATE KEY UPDATE
  check_in = VALUES(check_in),
  check_out = VALUES(check_out),
  status = VALUES(status),
  room_id = VALUES(room_id),
  paid_amount = VALUES(paid_amount),
  total_amount = VALUES(total_amount),
  reg_date = VALUES(reg_date);

UPDATE ah_booking SET cancel_reason = 'Đổi lịch công tác' WHERE booking_id = 'BKDEMO07';

INSERT INTO ah_booking_service (booking_id, service_id, qty, unit_price, amount, note)
SELECT 'BKDEMO02', 'SV01', 2, 150000, 300000, 'Bữa sáng hai suất'
WHERE NOT EXISTS (
  SELECT 1 FROM ah_booking_service WHERE booking_id = 'BKDEMO02' AND service_id = 'SV01'
);

INSERT INTO ah_booking_service (booking_id, service_id, qty, unit_price, amount, note)
SELECT 'BKDEMO04', 'SV01', 1, 150000, 150000, 'Bữa sáng'
WHERE NOT EXISTS (
  SELECT 1 FROM ah_booking_service WHERE booking_id = 'BKDEMO04' AND service_id = 'SV01'
);

INSERT INTO ah_payment (payment_id, booking_id, method, amount, status, txn_ref, note, reg_id, reg_date)
VALUES
  ('PAYDEMO01', 'BKDEMO01', 'VNPAY', 480000, 'SUCCESS', 'VNP2601', 'Cọc 30%', 'thuha', NOW()),
  ('PAYDEMO02', 'BKDEMO02', 'VNPAY', 1170000, 'SUCCESS', 'VNP2602', 'Cọc online', 'quochuy', DATE_SUB(NOW(), INTERVAL 1 DAY)),
  ('PAYDEMO03', 'BKDEMO03', 'CASH', 1600000, 'SUCCESS', NULL, 'Thu đủ khi nhận phòng', 'letan', DATE_SUB(NOW(), INTERVAL 2 DAY)),
  ('PAYDEMO04', 'BKDEMO04', 'VNPAY', 2550000, 'SUCCESS', 'VNP2604', 'Thanh toán đủ', 'guest', DATE_SUB(NOW(), INTERVAL 14 DAY)),
  ('PAYDEMO05', 'BKDEMO05', 'VNPAY', 1200000, 'SUCCESS', 'VNP2605', 'Cọc 30%', 'guest', DATE_SUB(NOW(), INTERVAL 2 DAY))
ON DUPLICATE KEY UPDATE amount = VALUES(amount), status = 'SUCCESS', reg_date = VALUES(reg_date);

INSERT INTO ah_review (review_id, booking_id, user_id, room_type_id, rating, comment, admin_reply, del_yn, reg_date)
VALUES (
  'RVDEMO01', 'BKDEMO04', 'guest', 'RT02', 5,
  'Phòng sạch, giường êm và lễ tân hướng dẫn rất rõ giờ nhận trả.',
  'Cảm ơn anh Khôi. Alice Hotel rất vui được đón anh lần sau.',
  'N', DATE_SUB(NOW(), INTERVAL 11 DAY)
)
ON DUPLICATE KEY UPDATE comment = VALUES(comment), admin_reply = VALUES(admin_reply), rating = VALUES(rating);
