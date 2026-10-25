-- Dashboard = Trang chủ (top-level). Hide empty "Báo cáo" group + legacy Home.

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

-- Soft-hide legacy Home (old landing) and empty Báo cáo folder
UPDATE MENU SET DELETE_FLAG = 'Y', DEL_YN = 'Y' WHERE ID IN (1, 23);

INSERT IGNORE INTO MENU_ROLE (MENU_ID, ROLE_ID) VALUES (16, 1);
