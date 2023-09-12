package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.ChangePasswordDto;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.dto.RestorePasswordDto;
import project.vilsoncake.Flowt.dto.UserDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, String> addUser(RegistrationDto registrationDto);
    UserDto getAuthenticatedUserDto(String authHeader);
    UserDto getUserDtoByUsername(String username);
    UserEntity getUserByUsername(String username);
    Map<String, List<String>> getUserSubscribesUsernames(String authHeader);
    Map<String, List<String>> getUserFollowersUsernames(String authHeader);
    Map<String, List<String>> getUserSongs(String authHeader);
    Map<String, List<PlaylistEntity>> getUserPlaylists(String authHeader);
    Map<String, String> changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto);
    Map<String, String> restorePassword(RestorePasswordDto restorePasswordDto);
    Map<String, String> deleteUser(String username);
}
