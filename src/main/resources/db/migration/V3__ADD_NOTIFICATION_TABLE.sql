CREATE TABLE IF NOT EXISTS notification (
    id SERIAL PRIMARY KEY,
    type varchar(64) NOT NULL,
    message TEXT NOT NULL,
    user_id BIGINT NOT NULL
);