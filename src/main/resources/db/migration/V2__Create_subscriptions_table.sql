CREATE TYPE digital_service AS ENUM (
    'YouTube Premium',
    'VK Музыка',
    'Яндекс.Плюс',
    'Netflix',
    'Spotify',
    'Apple Music'
);


CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    service_name digital_service NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_subscriptions_service_name ON subscriptions(service_name);
