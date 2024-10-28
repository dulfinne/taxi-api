CREATE TABLE passenger_info
(
    id           BIGINT PRIMARY KEY,
    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    phone_number VARCHAR(255),
    payment      VARCHAR(10) NOT NULL,
    ride_count   VARCHAR(255)
);

ALTER TABLE passenger_info
    ADD CONSTRAINT fk_passenger_info_id
        FOREIGN KEY (id) REFERENCES passenger (id)
            ON DELETE CASCADE;
