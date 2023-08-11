CREATE TABLE hibernate_sequence (
    next_val BIGINT
);

INSERT INTO hibernate_sequence VALUES ( 1 );
INSERT INTO hibernate_sequence VALUES ( 1 );

CREATE TABLE user_ (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(32) NOT NULL UNIQUE,
    email VARCHAR(32) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    description TEXT NOT NULL DEFAULT '',
    region TEXT NOT NULL DEFAULT 'Earth',
    email_verify BOOLEAN NOT NULL DEFAULT FALSE,
    artist_verify BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_role (
    user_id BIGINT NOT NULL UNIQUE,
    roles VARCHAR(64) NOT NULL
);

CREATE TABLE user_avatar (
    avatar_id SERIAL PRIMARY KEY,
    filename TEXT NOT NULL UNIQUE,
    size BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE subscribe (
    id BIGINT PRIMARY KEY,
    subscribed_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE follower (
    id SERIAL PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE liked (
    liked_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL
);

CREATE TABLE song (
    song_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    issue_year varchar(32) NOT NULL,
    genre TEXT NOT NULL,
    listens BIGINT NOT NULL DEFAULT 0,
    user_id BIGINT NOT NULL
);

CREATE TABLE song_avatar (
    avatar_id SERIAL PRIMARY KEY,
    filename TEXT NOT NULL UNIQUE,
    size BIGINT NOT NULL,
    song_id BIGINT NOT NULL
);

CREATE TABLE audio_file (
    file_id SERIAL PRIMARY KEY,
    filename TEXT NOT NULL UNIQUE,
    size BIGINT NOT NULL,
    song_id BIGINT NOT NULL
);

CREATE TABLE playlist (
    playlist_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE playlist_avatar (
    avatar_id SERIAL PRIMARY KEY,
    filename TEXT NOT NULL UNIQUE,
    size BIGINT NOT NULL,
    playlist_id BIGINT NOT NULL
);
