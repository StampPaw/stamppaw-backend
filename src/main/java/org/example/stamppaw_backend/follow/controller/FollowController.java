package org.example.stamppaw_backend.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.follow.dto.response.FollowResponse;
import org.example.stamppaw_backend.follow.service.FollowService;
import org.example.stamppaw_backend.user.service.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우
    @PostMapping("/{followingId}")
    public ResponseEntity<FollowResponse> follow(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long followingId
    ) {
        FollowResponse response = followService.follow(userDetails.getUser(), followingId);
        return ResponseEntity.ok(response);
    }

    // 언팔로우
    @DeleteMapping("/{followingId}")
    public ResponseEntity<String> unfollow(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long followingId
    ) {
        followService.unfollow(userDetails.getUser(), followingId);
        return ResponseEntity.ok("언팔로우 성공");
    }

    // 팔로잉 목록
    @GetMapping("/following")
    public ResponseEntity<List<FollowResponse>> getFollowingList(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<FollowResponse> followingList = followService.getFollowingList(userDetails.getUser());
        return ResponseEntity.ok(followingList);
    }

    // 팔로워 목록
    @GetMapping("/follower")
    public ResponseEntity<List<FollowResponse>> getFollowerList(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<FollowResponse> followerList = followService.getFollowerList(userDetails.getUser());
        return ResponseEntity.ok(followerList);
    }
}
