CREATE TABLE passenger_rating
(
    id           BIGSERIAL PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    rating       INTEGER NOT NULL,
    feedback     VARCHAR(255)
);

ALTER TABLE passenger_rating
    ADD CONSTRAINT fk_passenger_rating_passenger_info_id
        FOREIGN KEY (passenger_id) REFERENCES passenger_info (id)
            ON DELETE CASCADE;
