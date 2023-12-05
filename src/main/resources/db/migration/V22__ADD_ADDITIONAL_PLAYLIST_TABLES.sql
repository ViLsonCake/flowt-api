CREATE TABLE IF NOT EXISTS saved_playlist (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS last_listened_playlist (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE
);