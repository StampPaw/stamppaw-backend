package org.example.stamppaw_backend.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.stamppaw_backend.follow.entity.Follow;

@Getter
@Builder
@AllArgsConstructor
public class FollowResponse {

    private Long followId;      // 팔로우 관계 ID
    private Long followerId;    // 팔로워 ID
    private Long followingId;   // 팔로잉 ID
    private String followingNickname; // 닉네임 (상황에 따라 다르게 매핑됨)

    // 팔로잉 목록 조회
    public static FollowResponse from(Follow follow) {
        return FollowResponse.builder()
            .followId(follow.getId())
            .followerId(follow.getFollower().getId())
            .followingId(follow.getFollowing().getId())
            .followingNickname(follow.getFollowing().getNickname()) // 내가 팔로우한 사람
            .build();
    }

    // 팔로워 목록 조회
    public static FollowResponse fromFollowerView(Follow follow) {
        return FollowResponse.builder()
            .followId(follow.getId())
            .followerId(follow.getFollower().getId())
            .followingId(follow.getFollowing().getId())
            .followingNickname(follow.getFollower().getNickname()) // 나를 팔로우한 사람
            .build();
    }
}
