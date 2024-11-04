CREATE TABLE driver_rating
(
    id             BIGSERIAL PRIMARY KEY,
    rating         INTEGER NOT NULL,
    feedback       TEXT,
    driver_info_id BIGINT,
    CONSTRAINT fk_driver_info
        FOREIGN KEY (driver_info_id)
            REFERENCES driver_info (id)
            ON DELETE CASCADE
)