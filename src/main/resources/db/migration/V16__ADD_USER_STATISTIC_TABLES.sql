CREATE TABLE IF NOT EXISTS user_listened_statistic (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS listened (
    id SERIAL PRIMARY KEY,
    song_name TEXT NOT NULL,
    genre TEXT NOT NULL,
    author TEXT NOT NULL
);