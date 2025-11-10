package org.example.stamppaw_backend.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.follow.dto.response.FollowResponse;
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

    // 팔로우
    public FollowResponse follow(User follower, Long followingId) {
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

        // 자기 자신 팔로우 방지
        if (follower.getId().equals(followingId)) {
            throw new StampPawException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        // 중복 팔로우 방지
        if (followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new StampPawException(ErrorCode.ALREADY_FOLLOWING);
        }

        Follow follow = Follow.builder()
            .follower(follower)
            .following(following)
            .build();

        followRepository.save(follow);
        return FollowResponse.from(follow);
    }

    // 언팔로우
    public void unfollow(User follower, Long followingId) {
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
            .orElseThrow(() -> new StampPawException(ErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }

    // 팔로잉 목록 (내가 팔로우한 사람들)
    public List<FollowResponse> getFollowingList(User follower) {
        return followRepository.findByFollower(follower).stream()
            .map(FollowResponse::from) // from(): following 기준 닉네임 표시
            .collect(Collectors.toList());
    }

    // 팔로워 목록 (나를 팔로우한 사람들)
    public List<FollowResponse> getFollowerList(User following) {
        return followRepository.findByFollowing(following).stream()
            .map(FollowResponse::fromFollowerView) // 새 메서드로 follower 닉네임 표시
            .collect(Collectors.toList());
    }
}
