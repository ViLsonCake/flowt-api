package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.entity.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, String> addUser(RegistrationDto registrationDto) throws IOException;
    AuthenticatedUserDto getAuthenticatedUserDto(String authHeader);
    ExtendedUserDto getAuthenticatedUserDtoByUsername(String username);
    UserDto getPublicUserDtoByUsername(String username);
    UsersPageDto getUsersDtoBySubstring(String substring, int page, int size);
    UserEntity getUserByUsername(String username);
    UserEntity getUserByEmail(String email);
    Map<String, List<NotificationEntity>> getUserNotifications(String authHeader);
    Map<String, List<PlaylistDto>> getUserPlaylists(String authHeader);
    LastListenedSongsDto getLastListenedSongs(String authHeader);
    LastListenedPlaylistsDto getLastListenedPlaylists(String authHeader);
    Map<String, String> changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto);
    Map<String, String> restorePassword(RestorePasswordDto restorePasswordDto);
    Map<String, String> deleteUser(String username);
}
