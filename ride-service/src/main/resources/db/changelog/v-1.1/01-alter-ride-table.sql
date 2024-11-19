ALTER TABLE rides DROP COLUMN passenger_id;
ALTER TABLE rides DROP COLUMN driver_id;

ALTER TABLE rides
    ADD COLUMN passenger_username VARCHAR;
UPDATE rides SET passenger_username = 'default_user' WHERE passenger_username IS NULL;
ALTER TABLE rides ALTER COLUMN passenger_username SET NOT NULL;

ALTER TABLE rides
    ADD COLUMN driver_username VARCHAR;

CREATE INDEX idx_passenger_username ON rides (passenger_username);
CREATE INDEX idx_driver_username ON rides (driver_username);