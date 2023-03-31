DROP DATABASE IF EXISTS svatba_lubosovo_net;

CREATE DATABASE IF NOT EXISTS svatba_lubosovo_net
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

USE svatba_lubosovo_net;

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users
(
    id           INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name         VARCHAR(64) NOT NULL,
    phone        VARCHAR(16) DEFAULT NULL,
    email        VARCHAR(64) DEFAULT NULL,
    login        VARCHAR(32) DEFAULT NULL,
    password     VARCHAR(32) DEFAULT NULL,
    PRIMARY KEY  (id),
    UNIQUE       (name),
    UNIQUE       (login)
)
ENGINE=InnoDB
COMMENT='User table';

CREATE INDEX idx_users_login
    USING BTREE
    ON users(login);

DROP TABLE IF EXISTS gifts;
CREATE TABLE IF NOT EXISTS gifts
(
    id           INT UNSIGNED NOT NULL AUTO_INCREMENT,
    id_user      INT UNSIGNED DEFAULT NULL,
    title        VARCHAR(255) NOT NULL,
    descrip      VARCHAR(2048) DEFAULT NULL,
    link         VARCHAR(2048) DEFAULT NULL,
    price        INT UNSIGNED DEFAULT NULL,
    thumb        BLOB DEFAULT NULL,
    PRIMARY KEY  (id),
    UNIQUE       (title),
    FOREIGN KEY  (id_user) REFERENCES users(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
ENGINE=InnoDB
COMMENT='Gift table';

CREATE INDEX idx_gifts_price
    USING BTREE
    ON gifts(price, title);
