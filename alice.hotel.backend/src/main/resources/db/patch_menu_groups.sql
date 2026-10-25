-- Grouped PMS menu (ezCloud-like IA). Safe to re-run.

INSERT INTO MENU (ID, SITE_TYPE, NAME, ROUTE, PARENT_ID, DISPLAY_ORDER, DELETE_FLAG, DEL_YN, VN_NAME, SCREEN_NAME, BLANK_SCREEN)
VALUES
  (20, 'A', 'Vận hành', NULL, NULL, '3', 'N', 'N', 'Vận hành', NULL, NULL),
  (21, 'A', 'Kho phòng', NULL, NULL, '4', 'N', 'N', 'Kho phòng', NULL, NULL),
  (22, 'A', 'Bán hàng', NULL, NULL, '5', 'N', 'N', 'Bán hàng', NULL, NULL),
  (24, 'A', 'Cấu hình', NULL, NULL, '7', 'N', 'N', 'Cấu hình', NULL, NULL)
ON DUPLICATE KEY UPDATE
  NAME = VALUES(NAME), VN_NAME = VALUES(VN_NAME), PARENT_ID = NULL,
  DISPLAY_ORDER = VALUES(DISPLAY_ORDER), DELETE_FLAG = 'N', DEL_YN = 'N';

INSERT IGNORE INTO MENU_ROLE (MENU_ID, ROLE_ID) VALUES (20,1),(21,1),(22,1),(24,1),(16,1);

-- Children under groups
UPDATE MENU SET PARENT_ID = 20, NAME = 'Lễ tân', VN_NAME = 'Lễ tân', DISPLAY_ORDER = '1', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 14;
UPDATE MENU SET PARENT_ID = 20, NAME = 'Đặt phòng', VN_NAME = 'Đặt phòng', DISPLAY_ORDER = '2', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 15;

UPDATE MENU SET PARENT_ID = 21, NAME = 'Loại phòng', VN_NAME = 'Loại phòng', DISPLAY_ORDER = '1', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 10;
UPDATE MENU SET PARENT_ID = 21, NAME = 'Phòng', VN_NAME = 'Phòng', DISPLAY_ORDER = '2', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 11;
UPDATE MENU SET PARENT_ID = 21, NAME = 'Bảng giá', VN_NAME = 'Bảng giá', DISPLAY_ORDER = '3', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 18;

UPDATE MENU SET PARENT_ID = 22, NAME = 'Dịch vụ', VN_NAME = 'Dịch vụ', DISPLAY_ORDER = '1', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 12;
UPDATE MENU SET PARENT_ID = 22, NAME = 'Mã giảm giá', VN_NAME = 'Mã giảm giá', DISPLAY_ORDER = '2', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 13;

-- Dashboard = Trang chủ (top-level leaf)
UPDATE MENU
SET
  PARENT_ID = NULL,
  NAME = 'Trang chủ',
  VN_NAME = 'Trang chủ',
  ROUTE = '/',
  SCREEN_NAME = 'home',
  DISPLAY_ORDER = '1',
  DELETE_FLAG = 'N',
  DEL_YN = 'N'
WHERE ID = 16;

UPDATE MENU SET PARENT_ID = 24, NAME = 'Khách sạn', VN_NAME = 'Khách sạn', DISPLAY_ORDER = '1', DELETE_FLAG = 'N', DEL_YN = 'N' WHERE ID = 17;

-- Soft-hide legacy demo hotel-rooms, old Home, empty Báo cáo group
UPDATE MENU SET DELETE_FLAG = 'Y', DEL_YN = 'Y' WHERE ID IN (1, 2, 23);
