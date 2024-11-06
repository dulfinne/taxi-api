CREATE TABLE car
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255),
    color               VARCHAR(50),
    registration_number VARCHAR(50) NOT NULL UNIQUE,
    car_category        VARCHAR(50)
);

CREATE INDEX idx_registration_number ON car (registration_number);
