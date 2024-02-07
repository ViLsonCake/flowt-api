### Request mapping
___
Permit all
+ POST /auth/registration - registration new user
+ POST /auth/login - create access and refresh jwt tokens
+ POST /auth/oauth/google - login user by google authentication
+ GET /auth/refresh - refresh access and refresh tokens
+ GET /images/user/avatar/:_**username**_ - get user avatar by username
+ GET /images/user/profile-header/:_**username**_ - get user profile header by username
+ GET /images/song/:_**username**_/:_**name**_ - get song avatar by author and name
+ GET /images/playlist/:_**username**_/:_**name**_ - get playlist avatar by username and name
+ POST /verify/restore-password - send mail with code for restoring the password by email from request
+ GET /users/public/:**_username_** - get public user dto
+ GET /users/subscribes/:**_username_** - get user subscribes by username
+ GET /users/followers/:**_username_** - get user followers by username
+ POST /users/restore-password - change user password
+ GET /songs/info/:**_username_**/:_**name**_ - get song info by author and song name
+ GET /songs/audio/:**_username_**/:**_name_** - get song audio file by author and song name
+ GET /songs/random/:**_genre_** - get random song by genre
+ GET /songs/user-songs/:**_username_** - get user songs by username
+ GET /search/songs - search songs by substring order by listens count
+ GET /search/playlists - search playlists by substring
+ GET /search/users - search users by substring order by follower count
___
Authenticated
+ POST /users/avatar - change user avatar file
+ POST /users/avatar/url - change user avatar url
+ POST /users/profile-header - change user profile header
+ POST /users/change-password - change authenticated user password
+ POST /users/subscribe/:**_username_** - subscribe authenticated user to another user
+ POST /users/unsubscribe/:**_username_** - unsubscribe authenticated user to another user
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
+ GET /users/last-listened/songs - get authenticated user 15 last listened songs
+ GET /users/last-listened/playlists - get authenticated user 15 last listened playlists
+ GET /users/saved-playlists - get authenticated user saved playlists
+ GET /verify/email - verify user email
+ GET /verify/password - send mail with code for restoring the password
+ POST /verify/artist - send verify artist request
+ GET /songs/:**_genre_** - get songs list by genre
+ POST /songs - add new song entity
+ POST /songs/audio/:**_name_** - add to song audio file
+ POST /songs/avatar/:**_name_** - add avatar to song
+ PATCH /songs/statistic/:**_username_**/:**_name_** - update song statistic
+ DELETE /songs/:**_name_** - remove song by song name
+ POST /liked/:**_username_**/:**_name_** - add song to authenticated user liked
+ DELETE /liked/:**_username_**/:**_name_** - remove song from authenticated user liked
+ POST /playlists - create new playlist
+ POST /playlists/avatar/:**_playlistName_** - add avatar to playlist
+ POST /playlists/:**_playlistName_**/:**_author_**/:**_songName_** - add song to playlist
+ POST /playlists/:**_playlistName_** - add songs list to playlist
+ PATCH /playlists - change playlist name
+ PATCH /playlists/:**_playlistName_** - change playlist access modifier
+ DELETE /playlists/:**_playlistName_**/:**_author_**/:**_songName_** - remove song from playlist
+ DELETE /playlists/:**_playlistName_** - remove user playlist
+ DELETE /notifications/:**_id_** - remove user notification by id
+ POST /reports - send report to entity
+ GET /recommendations - get user recommendations song
+ GET /recommendations/might-like - get songs that the user might like
+ GET /artist-statistic/overall - get overall user songs statistic
+ GET /artist-statistic/popular - get most popular user songs
+ POST /saved-playlists - add playlist to saved
+ DELETE /saved-playlists - remove playlist from saved
___
For moderators
+ POST /moderator/warning-mail/:**_username_** - send to user warning mail
+ PATCH /moderator/active/:**_username_** - change user active
+ GET /reports/:**_type_** - get report by type
+ PATCH /reports/:**_id_** - access report by id
+ DELETE /report/:**_id_** - decline report by id
___
For admins
+ GET /admin/user/:**_username_** - get user by username
+ PATCH /admin/user/:**_username_** - add to user moderator authority
+ DELETE /admin/user/:**_username_** - delete user by username
