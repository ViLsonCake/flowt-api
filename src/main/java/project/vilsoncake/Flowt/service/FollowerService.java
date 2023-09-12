package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.FollowersDto;
import project.vilsoncake.Flowt.dto.SubscribesDto;

public interface FollowerService {
    boolean unsubscribeUser(Long userId, Long followerId);
    FollowersDto getUserFollowers(String authHeader, int page, int size);
    SubscribesDto getUserSubscribes(String authHeader, int page, int size);
}
