CREATE TABLE IF NOT EXISTS profile_hat (
    id SERIAL PRIMARY KEY,
    filename TEXT NOT NULL,
    size BIGINT NOT NULL,
    user_id BIGINT NOT NULL UNIQUE
);