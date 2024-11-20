ALTER TABLE passenger_info RENAME TO passenger;

ALTER TABLE passenger DROP COLUMN passenger_id;
ALTER TABLE passenger DROP COLUMN average_rating;

ALTER TABLE passenger
    ADD COLUMN username VARCHAR NOT NULL UNIQUE;
ALTER TABLE passenger
    ADD COLUMN sum_of_ratings DECIMAL;
ALTER TABLE passenger
    ADD COLUMN number_of_ratings INTEGER;

CREATE INDEX idx_username ON passenger (username);