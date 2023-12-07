CREATE TABLE IF NOT EXISTS song_region_statistic (
    id SERIAL PRIMARY KEY,
    song_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS listening (
    id SERIAL PRIMARY KEY,
    region TEXT NOT NULL,
    country TEXT NOT NULL,
    username TEXT NOT NULL,
    song_statistic_id BIGINT NOT NULL
);