CREATE TABLE driver_info
(
    id                BIGSERIAL PRIMARY KEY,
    driver_id         BIGINT NOT NULL UNIQUE,
    first_name        VARCHAR(100),
    last_name         VARCHAR(100),
    phone_number      VARCHAR(15),
    experience        INTEGER,
    sum_of_ratings    DECIMAL,
    number_of_ratings INTEGER,
    car_id            INTEGER,
    CONSTRAINT fk_car
        FOREIGN KEY (car_id)
            REFERENCES car (id)
            ON DELETE SET NULL
);

CREATE INDEX idx_phone_number ON driver_info (phone_number);
