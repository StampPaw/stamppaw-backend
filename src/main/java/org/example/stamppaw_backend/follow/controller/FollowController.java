package org.example.stamppaw_backend.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.follow.service.FollowService;
import org.example.stamppaw_backend.user.service.CustomUserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingId}")
    public ResponseEntity<String> follow(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long followingId) {

        followService.follow(userDetails.getUser(), followingId);
        return ResponseEntity.ok("팔로우 성공");
    }

    // 언팔로우
    @DeleteMapping("/{followingId}")
    public ResponseEntity<String> unfollow(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long followingId) {

        followService.unfollow(userDetails.getUser(), followingId);
        return ResponseEntity.ok("언팔로우 성공");
    }

    // 팔로잉 목록 (내가 팔로우한 사람들)
    @GetMapping("/following")
    public ResponseEntity<List<Long>> getFollowingList(
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<Long> followingList = followService.getFollowingList(userDetails.getUser());
        return ResponseEntity.ok(followingList);
    }

    // 팔로워 목록 (나를 팔로우한 사람들)
    @GetMapping("/follower")
    public ResponseEntity<List<Long>> getFollowerList(
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<Long> followerList = followService.getFollowerList(userDetails.getUser());
        return ResponseEntity.ok(followerList);
    }
}

