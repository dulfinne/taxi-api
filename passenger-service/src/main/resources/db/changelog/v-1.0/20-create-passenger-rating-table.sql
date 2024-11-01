CREATE TABLE passenger_rating
(
    id           BIGSERIAL PRIMARY KEY,
    passenger_id BIGINT  NOT NULL,
    rating       INTEGER NOT NULL,
    feedback     TEXT,
    FOREIGN KEY (passenger_id) REFERENCES passenger_info (id) ON DELETE CASCADE
);
