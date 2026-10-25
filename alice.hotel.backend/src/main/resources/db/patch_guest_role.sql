-- Patch: ensure demo guest can login (run if already applied booking schema)
USE alice_hotel;

INSERT INTO SYS_USER_ROLE (RL_ID, USER_ID, USE_YN, PUBLIC_INFO)
SELECT '2', 'guest', 'Y', 'Y'
WHERE NOT EXISTS (
  SELECT 1 FROM SYS_USER_ROLE WHERE USER_ID = 'guest' AND RL_ID = '2'
);
