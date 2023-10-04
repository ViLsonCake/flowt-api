CREATE TABLE IF NOT EXISTS report (
    id SERIAL PRIMARY KEY,
    whom_type varchar(64) NOT NULL,
    content_type varchar(64) NOT NULL,
    created_at TIMESTAMP,
    whom_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL
);