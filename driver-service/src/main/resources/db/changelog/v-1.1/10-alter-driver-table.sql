ALTER TABLE driver
    ALTER COLUMN phone_number SET NOT NULL;

ALTER TABLE driver
    ADD CONSTRAINT unique_phone_number UNIQUE (phone_number);

CREATE INDEX idx_username ON driver (username);
