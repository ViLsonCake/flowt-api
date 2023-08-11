INSERT INTO user_ (username, email, password, active)
VALUES ('admin', 'admin@gmail.com', '$2a$10$.DxdDNgzLHHJvaZUMIMm0ufo5Qlob.xeg/Sg8UUfEX9xAuCKxUgUu', true)
ON CONFLICT (username) DO NOTHING;

DELETE FROM user_role WHERE user_id = 1;
INSERT INTO user_role (user_id, roles)
VALUES (1, 'ADMIN');