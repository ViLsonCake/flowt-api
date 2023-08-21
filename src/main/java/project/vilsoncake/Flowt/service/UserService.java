package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.ChangePasswordDto;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface UserService {
    UserEntity addUser(RegistrationDto registrationDto);
    UserEntity getAuthenticatedUser(String authHeader);
    UserEntity getUserByUsername(String username);
    boolean changeUserPasswordByUsername(String authHeader, ChangePasswordDto changePasswordDto);

}
