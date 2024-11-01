ALTER TABLE IF EXISTS passenger_info
DROP
CONSTRAINT IF EXISTS fk_passenger_info_id;

DROP TABLE IF EXISTS passenger;
