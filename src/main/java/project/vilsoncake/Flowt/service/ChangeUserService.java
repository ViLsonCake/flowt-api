package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.DescriptionDto;
import project.vilsoncake.Flowt.dto.EmailDto;
import project.vilsoncake.Flowt.dto.RegionDto;
import project.vilsoncake.Flowt.dto.UsernameDto;

import java.util.Map;

public interface ChangeUserService {
    Map<String, String> changeUserUsername(String authHeader, UsernameDto newUsername);
    Map<String, String> changeUserEmail(String authHeader, EmailDto newEmail);
    Map<String, String> changeUserRegion(String authHeader, RegionDto newRegion);
    Map<String, String> changeUserDescription(String authHeader, DescriptionDto newDescription);
}
