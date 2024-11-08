CREATE
EXTENSION IF NOT EXISTS postgis;

CREATE TABLE rides
(
    id             SERIAL PRIMARY KEY,
    driver_id      BIGINT,
    passenger_id   BIGINT         NOT NULL,
    price          DECIMAL(19, 2) NOT NULL,
    start_time     TIMESTAMP WITH TIME ZONE,
    end_time       TIMESTAMP WITH TIME ZONE,
    start_position GEOGRAPHY(Point, 4326) NOT NULL,
    end_position   GEOGRAPHY(Point, 4326) NOT NULL,
    status         VARCHAR(255)   NOT NULL
);
