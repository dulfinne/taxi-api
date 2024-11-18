ALTER TABLE passenger RENAME TO passenger_info;

ALTER TABLE passenger_info
    ADD COLUMN passenger_id BIGINT UNIQUE NOT NULL;
ALTER TABLE passenger_info
    ADD COLUMN average_rating DECIMAL;

ALTER TABLE passenger_info DROP COLUMN username;
ALTER TABLE passenger_info DROP COLUMN sum_of_ratings;
ALTER TABLE passenger_info DROP COLUMN number_of_ratings;

DROP INDEX IF EXISTS idx_username;
