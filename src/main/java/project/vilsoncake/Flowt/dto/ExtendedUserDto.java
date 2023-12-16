package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedUserDto {
    private String username;
    private String email;
    private String region;
    private String description;
    private String avatar;
    private Set<Role> roles;
    private boolean userHaveAvatar;
    private boolean emailVerified;

    public static ExtendedUserDto fromUser(UserEntity user) {
        ExtendedUserDto extendedUserDto = new ExtendedUserDto();
        extendedUserDto.setUsername(user.getUsername());
        extendedUserDto.setEmail(user.getEmail());
        extendedUserDto.setRegion(user.getRegion());
        extendedUserDto.setDescription(user.getDescription());
        extendedUserDto.setAvatar(user.getUserAvatar().getAvatarUrl());
        extendedUserDto.setRoles(user.getRoles());
        extendedUserDto.setUserHaveAvatar(user.getUserAvatar().isUserHaveAvatar());
        extendedUserDto.setEmailVerified(user.isEmailVerify());
        return extendedUserDto;
    }
}
