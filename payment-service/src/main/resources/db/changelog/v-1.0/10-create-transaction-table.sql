CREATE TABLE transaction_history
(
    id               BIGSERIAL PRIMARY KEY,
    wallet_id        BIGINT                   NOT NULL,
    amount           DECIMAL(19, 2)           NOT NULL,
    transaction_time TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE transaction_history
    ADD CONSTRAINT fk_wallet FOREIGN KEY (wallet_id) REFERENCES wallet (id) ON DELETE CASCADE;
