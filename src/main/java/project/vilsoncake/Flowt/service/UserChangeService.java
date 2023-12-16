package project.vilsoncake.Flowt.service;

import jakarta.servlet.http.HttpServletResponse;
import project.vilsoncake.Flowt.dto.*;

import java.util.Map;

public interface UserChangeService {
    JwtResponse changeUserUsername(String authHeader, UsernameDto newUsername, HttpServletResponse response);
    Map<String, String> changeUserEmail(String authHeader, EmailDto newEmail);
    Map<String, String> changeUserRegion(String authHeader, RegionDto newRegion);
    Map<String, String> changeUserDescription(String authHeader, DescriptionDto newDescription);
    ExtendedUserDto changeUserAuthority(String username);
    Map<String, Boolean> changeUserActive(String username);
}
