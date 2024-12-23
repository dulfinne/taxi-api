CREATE TABLE wallet
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    balance  DECIMAL(19, 2) DEFAULT 0,
    debt     DECIMAL(19, 2) DEFAULT 0
);

ALTER TABLE wallet
    ADD CONSTRAINT unique_username UNIQUE (username);
