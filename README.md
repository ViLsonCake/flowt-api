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
+ GET /verify/email - verify user email
+ GET /verify/password - send mail with code for restoring the password
___ 
Only for admins
+ GET /admin/user/:username - get user by username
+ PATCH /admin/user/:username - add to user moderator authority
+ PATCH /admin/active/:username - change user active
+ DELETE /admin/user/:username - delete user by username
