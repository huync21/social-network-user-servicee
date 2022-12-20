INSERT INTO ROLE (NAME)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

-- INSERT INTO USER (USERNAME, PASSWORD,
--                   EMAIL, ENABLED, ACCOUNT_EXPIRED, CREDENTIALS_EXPIRED, ACCOUNT_LOCKED)
-- VALUES ('admin', '{bcrypt}$2a$12$pmbNwgMGCxAhkOTZIXMnH.3feQ6ZatnXXYRO0s91A9sBMq/vCamvu',
--         'huync21@gmail.com', 1, 0, 0, 0),
--        ('user', '{bcrypt}$2a$12$pmbNwgMGCxAhkOTZIXMnH.3feQ6ZatnXXYRO0s91A9sBMq/vCamvu',
--         'huy2110@gmail.com', 1, 0, 0, 0);

-- INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
-- VALUES (1, 1) /* role_admin assigned to admin user */,
--        (2, 2) /* role_user assigned to user user */ ;