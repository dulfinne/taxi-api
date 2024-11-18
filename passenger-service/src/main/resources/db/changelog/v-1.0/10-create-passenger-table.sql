CREATE TABLE passenger_info
(
    id             BIGSERIAL PRIMARY KEY,
    passenger_id   BIGINT UNIQUE NOT NULL,
    first_name     VARCHAR(255),
    last_name      VARCHAR(255),
    phone_number   VARCHAR(50) UNIQUE,
    payment        VARCHAR(50)   NOT NULL,
    ride_count     INTEGER,
    average_rating DECIMAL
);

CREATE INDEX idx_phone_number ON passenger_info (phone_number);
