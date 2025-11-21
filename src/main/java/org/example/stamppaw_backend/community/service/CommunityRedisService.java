package org.example.stamppaw_backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CommunityRedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void increaseView(Long communityId, String sessionId) {
        String key = recentViewKey(communityId, sessionId);

        // 이미 조회한 경우
        Boolean exists = redisTemplate.hasKey(key);
        if(Boolean.TRUE.equals(exists)) return;

        // 조회수 증가
        redisTemplate.opsForValue().increment(viewCountKey(communityId));

        // 일정 시간 동안 재조회 불가
        redisTemplate.opsForValue().set(key, "1", Duration.ofHours(1));
    }

    public Long getViewCount(Long communityId) {
        Object value = redisTemplate.opsForValue().get(viewCountKey(communityId));
        return value == null ? 0L : Long.parseLong(value.toString());
    }

    public void resetView(Long communityId) {
        redisTemplate.delete(viewCountKey(communityId));
    }


    private String recentViewKey(Long communityId, String sessionId) {
        return "community:view:recent:" + communityId + ":" + sessionId;
    }

    private String viewCountKey(Long communityId) {
        return "community:view:" + communityId;
    }
}
