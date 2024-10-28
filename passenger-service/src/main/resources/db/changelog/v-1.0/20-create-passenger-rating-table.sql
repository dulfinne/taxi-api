CREATE TABLE passenger_rating
(
    id           BIGSERIAL PRIMARY KEY,
    passenger_id BIGINT,
    rating       BIGINT NOT NULL,
    feedback     TEXT
);

ALTER TABLE passenger_rating
    ADD CONSTRAINT fk_passenger_rating_passenger_id
        FOREIGN KEY (passenger_id) REFERENCES passenger (id)
            ON DELETE CASCADE;
