CREATE TABLE IF NOT EXISTS last_listened (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE
);