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
+ GET /users/authenticated - get authenticated user dto
+ GET /verify/email - send mail with verify-link
+ GET /verify/password - send mail with code for restoring the password
