DROP TABLE IF EXISTS user_role;

-- Make user id non unique
CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT NOT NULL,
    roles VARCHAR(64) NOT NULL
);

-- Delete admin user role and insert again
DELETE FROM user_role WHERE user_id = '1';

INSERT INTO user_role (user_id, roles)
VALUES (1, 'ADMIN');