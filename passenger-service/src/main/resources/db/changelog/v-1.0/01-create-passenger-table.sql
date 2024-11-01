CREATE TABLE passenger
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(20) UNIQUE NOT NULL,
    password   VARCHAR NOT NULL,
    email      VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL
);
