package org.example.stamppaw_backend.follow.service;


import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.follow.entity.Follow;
import org.example.stamppaw_backend.follow.repository.FollowRepository;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우 기능
    public void follow(User follower, Long followingId) {
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

        // 자기 자신을 팔로우하는 경우 방지
        if (follower.getId().equals(followingId)) {
            throw new StampPawException(ErrorCode.INVALID_REQUEST);
        }

        // 중복 팔로우 방지
        boolean alreadyFollowed = followRepository.findByFollowerAndFollowing(follower, following).isPresent();
        if (alreadyFollowed) {
            throw new StampPawException(ErrorCode.ALREADY_FOLLOWING);
        }

        Follow follow = Follow.builder()
            .follower(follower)
            .following(following)
            .build();

        followRepository.save(follow);
    }

    //언팔로우 기능
    public void unfollow(User follower, Long followingId) {
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
            .orElseThrow(() -> new StampPawException(ErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }

    // 팔로잉 목록 조회 (내가 팔로우한 유저들)
    public List<Long> getFollowingList(User follower) {
        return followRepository.findByFollower(follower).stream()
            .map(f -> f.getFollowing().getId())
            .collect(Collectors.toList());
    }

    // 팔로워 목록 조회 (나를 팔로우한 유저들)
    public List<Long> getFollowerList(User following) {
        return followRepository.findByFollowing(following).stream()
            .map(f -> f.getFollower().getId())
            .collect(Collectors.toList());
    }
}
