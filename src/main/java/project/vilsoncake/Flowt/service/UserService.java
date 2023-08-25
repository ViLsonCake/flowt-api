package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.ChangePasswordDto;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.dto.RestorePasswordDto;
import project.vilsoncake.Flowt.dto.UserDto;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface UserService {
    UserEntity addUser(RegistrationDto registrationDto);
    UserDto getAuthenticatedUserDto(String authHeader);
    UserEntity getUserByUsername(String username);
    boolean changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto);
    boolean restorePassword(RestorePasswordDto restorePasswordDto);

}
