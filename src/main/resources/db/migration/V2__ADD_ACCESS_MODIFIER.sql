ALTER TABLE playlist ADD is_private BOOLEAN DEFAULT TRUE;

UPDATE playlist set is_private=TRUE;