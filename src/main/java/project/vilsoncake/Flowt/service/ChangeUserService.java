package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.*;

import java.util.Map;

public interface ChangeUserService {
    Map<String, String> changeUserUsername(String authHeader, UsernameDto newUsername);
    Map<String, String> changeUserEmail(String authHeader, EmailDto newEmail);
    Map<String, String> changeUserRegion(String authHeader, RegionDto newRegion);
    Map<String, String> changeUserDescription(String authHeader, DescriptionDto newDescription);
    ChangeAuthorityDto changeUserAuthority(String username);
    Map<String, Boolean> changeUserActive(String username);
}
