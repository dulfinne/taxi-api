CREATE TABLE driver_rating
(
    id             BIGSERIAL PRIMARY KEY,
    rating         INTEGER NOT NULL,
    feedback       TEXT,
    driver_id BIGINT,
    CONSTRAINT fk_driver
        FOREIGN KEY (driver_id)
            REFERENCES driver (id)
            ON DELETE CASCADE
)