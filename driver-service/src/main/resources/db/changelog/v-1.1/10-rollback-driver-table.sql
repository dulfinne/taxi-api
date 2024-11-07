ALTER TABLE driver
    ALTER COLUMN phone_number DROP NOT NULL;

ALTER TABLE driver
DROP
CONSTRAINT unique_phone_number;

DROP INDEX idx_username;
