### Request mapping
___
Permit all
+ POST /auth/registration - registration new user
+ POST /auth/login - create access and refresh jwt tokens
+ POST /auth/oauth/google - login user by google authentication
+ GET /auth/refresh - refresh access and refresh tokens
+ GET /images/user/avatar/:username - get user avatar by username
+ GET /images/user/profile-header/:username - get user profile header by username
+ GET /images/song/:username/:name - get song avatar by author and name
+ GET /images/playlist/:username/:name - get playlist avatar by username and name
+ POST /verify/restore-password - send mail with code for restoring the password by email from request
+ POST /users/restore-password - change user password
+ GET /search/songs - search songs by substring
+ GET /search/playlist - search playlists by substring
+ GET /search/users - search users by substring
___
Authenticated
+ POST /users/avatar - change user avatar file
+ POST /users/avatar/url - change user avatar url
+ POST /users/profile-header - change user profile header
+ POST /users/change-password - change authenticated user password
+ POST /users/subscribe/:username - subscribe authenticated user to another user
+ POST /users/unsubscribe/:username - unsubscribe authenticated user to another user
+ PATCH /users/username - change user username
+ PATCH /users/email - change user email
+ PATCH /users/region - change user region
+ PATCH /users/description - change user description
+ GET /users/authenticated - get authenticated user dto
+ GET /users/subscribes - get authenticated user subscribes list
+ GET /users/followers - get authenticated user followers list
+ GET /users/liked - get authenticated user liked songs list
+ GET /users/songs - get authenticated user songs list
+ GET /users/playlists - get authenticated user playlists list
+ GET /users/notifications - get authenticated user notifications list
+ GET /users/last-listened - get authenticated user 15 last listened songs
+ GET /verify/email - verify user email
+ GET /verify/password - send mail with code for restoring the password
+ POST /verify/artist - send verify artist request
+ GET /songs/:username/:name - get song info by author and song name
+ GET /songs/audio/:username/:name - get song audio file by author and song name
+ GET /songs/random/:genre - get random song by genre
+ GET /songs/:genre - get songs list by genre
+ POST /songs - add new song info
+ POST /songs/audio/:name - add to song audio file
+ POST /songs/avatar/:name - add avatar to song
+ DELETE /songs/:name - remove song by song name
+ POST /liked/:username/:name - add song to authenticated user liked
+ DELETE /liked/:username/:name - remove song from authenticated user liked
+ POST /playlists - create new playlist
+ POST /playlists/avatar/:playlistName - add avatar to playlist
+ POST /playlists/:playlistName/:author/:songName - add song to playlist
+ PATCH /playlists - change playlist name
+ PATCH /playlists/:playlistName - change playlist access modifier
+ DELETE /playlists/:playlistName/:author/:songName - remove song from playlist
+ DELETE /notifications/:id - remove user notification by id
+ POST /reports - send report
+ GET /reports/:type - get report by type
+ PATCH /reports/:id - access report by id
+ DELETE /report/:id - cancel report by id
___ 
For moderators
+ POST /moderator/warning-mail/:username - send to user warning mail
+ PATCH /moderator/active/:username - change user active
___
Only for admins
+ GET /admin/user/:username - get user by username
+ PATCH /admin/user/:username - add to user moderator authority
+ DELETE /admin/user/:username - delete user by username
