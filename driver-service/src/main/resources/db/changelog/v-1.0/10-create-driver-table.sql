CREATE TABLE driver
(
    id                BIGSERIAL PRIMARY KEY,
    username          VARCHAR     NOT NULL UNIQUE,
    first_name        VARCHAR(100),
    last_name         VARCHAR(100),
    phone_number      VARCHAR(15) NOT NULL UNIQUE,
    experience        INTEGER,
    sum_of_ratings    DECIMAL,
    number_of_ratings INTEGER,
    car_id            BIGINT,
    CONSTRAINT fk_car
        FOREIGN KEY (car_id)
            REFERENCES car (id)
            ON DELETE SET NULL
);

CREATE INDEX idx_phone_number ON driver (phone_number);
CREATE INDEX idx_username ON driver (username);

