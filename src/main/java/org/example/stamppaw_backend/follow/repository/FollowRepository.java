package org.example.stamppaw_backend.follow.repository;

import java.util.List;
import java.util.Optional;
import org.example.stamppaw_backend.follow.entity.Follow;
import org.example.stamppaw_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 내가 팔로우한 사람
    List<Follow> findByFollower(User follower);
    // 나를 팔로우한 사람
    List<Follow> findByFollowing(User following);
    // 중복 팔로우 방지
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    // 언팔로우
    void deleteByFollowerAndFollowing(User follower, User following);

}
