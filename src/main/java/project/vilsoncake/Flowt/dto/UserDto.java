package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private String region;
    private String description;
    private String avatar;
    private boolean userHaveAvatar;
    private boolean emailVerified;

    public static UserDto fromUser(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRegion(user.getRegion());
        userDto.setDescription(user.getDescription());
        userDto.setAvatar(user.getUserAvatar().getAvatarUrl());
        userDto.setUserHaveAvatar(user.getUserAvatar().isUserHaveAvatar());
        userDto.setEmailVerified(user.isEmailVerify());
        return userDto;
    }
}
