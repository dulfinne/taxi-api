ALTER TABLE IF EXISTS passenger_info
DROP
CONSTRAINT IF EXISTS fk_passenger_info_id;

ALTER TABLE IF EXISTS passenger_rating
DROP
CONSTRAINT IF EXISTS fk_passenger_rating_passenger_info_id;

DROP TABLE IF EXISTS passenger_info;
