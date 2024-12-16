ALTER TABLE rides
    ADD COLUMN payment VARCHAR;

UPDATE rides SET payment = 1 WHERE payment IS NULL;

ALTER TABLE rides ALTER COLUMN payment SET NOT NULL;