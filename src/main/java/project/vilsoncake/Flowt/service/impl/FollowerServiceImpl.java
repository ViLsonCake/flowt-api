package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.FollowersDto;
import project.vilsoncake.Flowt.dto.SubscribesDto;
import project.vilsoncake.Flowt.dto.UserDto;
import project.vilsoncake.Flowt.entity.FollowerEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.FollowerRepository;
import project.vilsoncake.Flowt.service.FollowerService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;
    private final UserService userService;
    private final AuthUtils authUtils;

    @Override
    public boolean unsubscribeUser(Long userId, Long followerId) {
        followerRepository.deleteByUserAndFollowerId(userId, followerId);
        return true;
    }

    @Override
    public FollowersDto getAuthenticatedUserFollowers(String authHeader, int page, int size) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        return getUserFollowersByUsername(username, page, size);
    }

    @Override
    public SubscribesDto getAuthenticatedUserSubscribes(String authHeader, int page, int size) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        return getUserSubscribesByUsername(username, page, size);
    }

    @Override
    public FollowersDto getUserFollowersByUsername(String username, int page, int size) {
        UserEntity user = userService.getUserByUsername(username);
        Page<FollowerEntity> followers = followerRepository.findAllByFollowerId(user.getUserId(), PageRequest.of(page, size));

        return new FollowersDto(
                followers.getTotalPages(),
                followers.getContent().stream().map(follower -> UserDto.fromUser(follower.getUser())).toList()
        );
    }

    @Override
    public SubscribesDto getUserSubscribesByUsername(String username, int page, int size) {
        UserEntity user = userService.getUserByUsername(username);
        Page<FollowerEntity> followers = followerRepository.findAllByUserId(user.getUserId(), PageRequest.of(page, size));

        return new SubscribesDto(
                followers.getTotalPages(),
                followers.getContent().stream().map(subscribe -> UserDto.fromUser(subscribe.getFollower())).toList()
        );
    }
}
