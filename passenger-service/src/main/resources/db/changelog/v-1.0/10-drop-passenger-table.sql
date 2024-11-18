ALTER TABLE passenger_rating
DROP
CONSTRAINT IF EXISTS fk_passenger_id;

DROP TABLE IF EXISTS passenger_info;
