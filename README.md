# Flowt RESTful API service

Open source API for music service Flowt with MinIO integration for storing images and audio files, and Redis with Postgresql, the application runs inside a Docker container network.

## Installation
```cmd
docker-compose up --build
```

### Request mapping
___
Permit all
+ POST /auth/registration - registration new user
+ POST /auth/login - create access and refresh jwt tokens
+ GET /auth/refresh - refresh access and refresh tokens
+ GET /images/user/:username - get user avatar by username
+ POST /verify/restore-password - send mail with code for restoring the password by email from request
+ POST /users/restore-password - change user password 
___
Authenticated
+ POST /users/avatar - change user avatar
+ POST /users/change-password - change authenticated user password
+ POST /users/subscribe/:username - subscribe authenticated user to another user
+ POST /users/unsubscribe/:username - unsubscribe authenticated user to another user
+ PATCH /users/username - change user username
+ PATCH /users/email - change user email
+ PATCH /users/region - change user region
+ PATCH /users/description - change user description
+ GET /users/authenticated - get authenticated user dto
+ GET /users/subscribes - get authenticated user subscribe list
+ GET /users/followers - get authenticated user followers list
+ GET /users/liked - get authenticated user liked song list
+ GET /users/songs - get authenticated user song list
+ GET /verify/email - verify user email
+ GET /verify/password - send mail with code for restoring the password
+ GET /songs/:username/:name - get song info by author and song name
+ GET /songs/audio/:username/:name - get song audio file by author and song name
+ POST /songs - add new song info
+ POST /songs/audio/:name - add to song audio file
+ POST /songs/avatar/:name - add avatar to song
+ POST /liked/:username/:name - add song to authenticated user liked
+ DELETE /liked/:username/:name - remove song from authenticated user liked
+ POST /playlists - create new playlist
+ POST /playlists/avatar/:playlistName - add avatar to playlist
+ POST /playlists/:playlistName/:author/:songName - add song to playlist
+ DELETE /playlists/:playlistName/:author/:songName - remove song from playlist
___ 
For moderators
+ POST /warning-mail/:username - send to user warning mail
___
Only for admins
+ GET /admin/user/:username - get user by username
+ PATCH /admin/user/:username - add to user moderator authority
+ PATCH /admin/active/:username - change user active
+ DELETE /admin/user/:username - delete user by username
