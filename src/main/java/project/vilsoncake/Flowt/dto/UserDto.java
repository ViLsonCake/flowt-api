package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private String region;
    private List<SubscribeEntity> subscribers;
    private List<FollowerEntity> followers;

    public static UserDto fromUser(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRegion(user.getRegion());
        userDto.setSubscribers(user.getSubscribes());
        userDto.setFollowers(user.getFollowers());
        return userDto;
    }
}
