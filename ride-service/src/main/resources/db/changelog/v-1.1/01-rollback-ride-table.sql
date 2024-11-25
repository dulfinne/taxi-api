ALTER TABLE rides
    ADD COLUMN passenger_id BIGINT NOT NULL;
ALTER TABLE rides
    ADD COLUMN driver_id BIGINT;

ALTER TABLE rides DROP COLUMN passenger_username;
ALTER TABLE rides DROP COLUMN driver_username;

DROP INDEX IF EXISTS idx_passenger_username;
DROP INDEX IF EXISTS idx_driver_username;