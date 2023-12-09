package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.FollowersDto;
import project.vilsoncake.Flowt.dto.SubscribesDto;

public interface FollowerService {
    boolean unsubscribeUser(Long userId, Long followerId);
    FollowersDto getAuthenticatedUserFollowers(String authHeader, int page, int size);
    SubscribesDto getAuthenticatedUserSubscribes(String authHeader, int page, int size);
    FollowersDto getUserFollowersByUsername(String username, int page, int size);
    SubscribesDto getUserSubscribesByUsername(String username, int page, int size);
}
