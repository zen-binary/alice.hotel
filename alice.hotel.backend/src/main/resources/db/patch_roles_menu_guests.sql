-- Roles and menus, same model as internship: SYS_ROLE + MENU + MENU_ROLE.
-- Role 1 quản trị (full), role 2 khách (no admin menu), role 3 lễ tân (vận hành).

INSERT INTO SYS_ROLE (RL_ID, RL_NM, RL_CD, RL_DESC)
VALUES
  (1, 'Quản trị viên', 'ADMIN', 'Toàn bộ menu admin và phân quyền'),
  (2, 'Khách hàng', 'GUEST', 'Đặt phòng trên site khách, không vào admin'),
  (3, 'Lễ tân', 'FRONT', 'Vận hành, phòng và tài khoản khách')
ON DUPLICATE KEY UPDATE RL_NM = VALUES(RL_NM), RL_CD = VALUES(RL_CD), RL_DESC = VALUES(RL_DESC);

INSERT INTO MENU (ID, SITE_TYPE, NAME, ROUTE, PARENT_ID, DISPLAY_ORDER, DELETE_FLAG, DEL_YN, VN_NAME, SCREEN_NAME, BLANK_SCREEN)
VALUES
  (25, 'A', 'Khách hàng', NULL, NULL, '6', 'N', 'N', 'Khách hàng', NULL, NULL),
  (26, 'A', 'Tài khoản khách', '/guests', 25, '1', 'N', 'N', 'Tài khoản khách', 'guestAccount', NULL),
  (27, 'A', 'Hệ thống', NULL, NULL, '8', 'N', 'N', 'Hệ thống', NULL, NULL),
  (28, 'A', 'Quản lý menu', '/menu', 27, '1', 'N', 'N', 'Quản lý menu', 'pathMenu', NULL),
  (29, 'A', 'Tài nguyên', '/resource', 27, '2', 'N', 'N', 'Tài nguyên', 'resourceManagement', NULL),
  (30, 'A', 'Phân quyền vai trò', '/role-resource', 27, '3', 'N', 'N', 'Phân quyền vai trò', 'roleResourceManagement', NULL)
ON DUPLICATE KEY UPDATE
  NAME = VALUES(NAME),
  VN_NAME = VALUES(VN_NAME),
  ROUTE = VALUES(ROUTE),
  PARENT_ID = VALUES(PARENT_ID),
  SCREEN_NAME = VALUES(SCREEN_NAME),
  DISPLAY_ORDER = VALUES(DISPLAY_ORDER),
  DELETE_FLAG = 'N',
  DEL_YN = 'N';

-- Quản trị: menu vận hành hiện có + khách + hệ thống
INSERT IGNORE INTO MENU_ROLE (MENU_ID, ROLE_ID) VALUES
  (16,1),(20,1),(14,1),(15,1),(21,1),(10,1),(11,1),(18,1),(22,1),(12,1),(13,1),(24,1),(17,1),
  (25,1),(26,1),(27,1),(28,1),(29,1),(30,1);

-- Lễ tân: trang chủ, vận hành, kho phòng, tài khoản khách. Không cấu hình và không phân quyền.
INSERT IGNORE INTO MENU_ROLE (MENU_ID, ROLE_ID) VALUES
  (16,3),(20,3),(14,3),(15,3),(21,3),(10,3),(11,3),(25,3),(26,3);

INSERT INTO CPS_USER (USER_ID, USER_PW, USER_NO, USER_NAME, USER_DIV, DIVISION)
VALUES (
  'letan',
  '$2b$10$mmuHRyATxWsB99ovbpJ0HuRBgUtSB1B5Enb/qpz2Y0CcGBnaC55Te',
  'A002',
  'Lễ tân',
  'ADM',
  'ADMIN'
) ON DUPLICATE KEY UPDATE USER_NAME = VALUES(USER_NAME), USER_DIV = VALUES(USER_DIV);

INSERT INTO SYS_USER_ROLE (RL_ID, USER_ID, USE_YN, PUBLIC_INFO)
SELECT '3', 'letan', 'Y', 'Y'
WHERE NOT EXISTS (
  SELECT 1 FROM SYS_USER_ROLE WHERE USER_ID = 'letan' AND RL_ID = '3'
);
