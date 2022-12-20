-- CREATE TABLE PERMISSION
-- (
--     ID   INT PRIMARY KEY AUTO_INCREMENT,
--     NAME VARCHAR(60) UNIQUE KEY
-- );
--
-- INSERT INTO PERMISSION (NAME)
-- VALUES ('can_create_user'),
--        ('can_update_user'),
--        ('can_read_user'),
--        ('can_delete_user');

CREATE TABLE ROLE
(
    ID   INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(60) UNIQUE KEY
);


-- CREATE TABLE PERMISSION_ROLE
-- (
--     PERMISSION_ID INT,
--     FOREIGN KEY (PERMISSION_ID) REFERENCES PERMISSION (ID),
--     ROLE_ID       INT,
--     FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID)
-- );
--
-- INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID)
-- VALUES (1, 1), /* can_create_user assigned to role_admin */
--        (2, 1), /* can_update_user assigned to role_admin */
--        (3, 1), /* can_read_user assigned to role_admin */
--        (4, 1), /* can_delete_user assigned to role_admin */
--
--        (3, 2); /* can_read_user assigned to role_user */

CREATE TABLE USER
(
    ID                  VARCHAR(36) PRIMARY KEY,
    USERNAME            VARCHAR(24) UNIQUE KEY NOT NULL,
    PASSWORD            VARCHAR(255) NOT NULL,
    AVATAR              VARCHAR(255),
    DESCRIPTION         VARCHAR(255),
    ENABLED             BIT(1)       NOT NULL,
    ACCOUNT_EXPIRED     BIT(1)       NOT NULL,
    CREDENTIALS_EXPIRED BIT(1)       NOT NULL,
    ACCOUNT_LOCKED      BIT(1)       NOT NULL,
    created_date       datetime     null,
    created_by          varchar(255) null,
    last_modified_date datetime     null,
    last_modified_by   varchar(256) null
);



CREATE TABLE ROLE_USER
(
    ROLE_ID INT,
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID),
    USER_ID VARCHAR(36),
    FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);



CREATE TABLE USER_NAME_CHANGE_LOG
(
    ID            INT PRIMARY KEY AUTO_INCREMENT,
    OLD_USER_NAME VARCHAR(24),
    NEW_USER_NAME VARCHAR(24),
    USER_ID       VARCHAR(36),
    created_date       datetime     null,
    created_by          varchar(255) null,
    last_modified_date datetime     null,
    last_modified_by   varchar(256) null,
    FOREIGN KEY (USER_ID) REFERENCES USER (ID)
)