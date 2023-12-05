package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.*;
import project.vilsoncake.Flowt.entity.*;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, String> addUser(RegistrationDto registrationDto);
    UserDto getAuthenticatedUserDto(String authHeader);
    UserDto getUserDtoByUsername(String username);
    UsersPageDto getUsersDtoBySubstring(SubstringDto substring, int page, int size);
    UserEntity getUserByUsername(String username);
    UserEntity getUserByEmail(String email);
    Map<String, List<NotificationEntity>> getUserNotifications(String authHeader);
    Map<String, List<PlaylistEntity>> getUserPlaylists(String authHeader);
    LastListenedSongsDto getLastListenedSongs(String authHeader);
    LastListenedPlaylistsDto getLastListenedPlaylists(String authHeader);
    Map<String, String> changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto);
    Map<String, String> restorePassword(RestorePasswordDto restorePasswordDto);
    Map<String, String> deleteUser(String username);
}
