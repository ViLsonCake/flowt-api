package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUserDto {
    private String username;
    private String email;
    private String region;
    private String description;
    private String avatar;
    private boolean userHaveAvatar;
    private boolean emailVerified;

    public static AuthenticatedUserDto fromUser(UserEntity user) {
        AuthenticatedUserDto authenticatedUserDto = new AuthenticatedUserDto();
        authenticatedUserDto.setUsername(user.getUsername());
        authenticatedUserDto.setEmail(user.getEmail());
        authenticatedUserDto.setRegion(user.getRegion());
        authenticatedUserDto.setDescription(user.getDescription());
        authenticatedUserDto.setAvatar(user.getUserAvatar().getAvatarUrl());
        authenticatedUserDto.setUserHaveAvatar(user.getUserAvatar().isUserHaveAvatar());
        authenticatedUserDto.setEmailVerified(user.isEmailVerify());
        return authenticatedUserDto;
    }
}
