CREATE TABLE IF NOT EXISTS artist_verify_request (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    user_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS personal_data (
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    birth_date VARCHAR(32) NOT NULL,
    sex VARCHAR(8) NOT NULL,
    country TEXT NOT NULL,
    passport_number TEXT NOT NULL,
    request_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS link (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    request_id BIGINT NOT NULL UNIQUE
);