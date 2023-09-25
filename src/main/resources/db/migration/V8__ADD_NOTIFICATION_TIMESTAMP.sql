ALTER TABLE notification ADD created_at TIMESTAMP;

-- Set current date to existing notifications
UPDATE notification SET created_at = now();