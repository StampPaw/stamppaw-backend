package org.example.stamppaw_backend.community.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.community.entity.Community;
import org.example.stamppaw_backend.community.repository.CommunityRepository;
import org.example.stamppaw_backend.community.service.CommunityRedisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommunitySyncScheduler {
    private final CommunityRepository communityRepository;
    private final CommunityRedisService communityRedisService;

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void syncViewsToDB() {
        List<Community> communities = communityRepository.findAll();

        for(Community community : communities) {
            Long redisViews = communityRedisService.getViewCount(community.getId());

            if(redisViews > 0) {
                community.updateViews(redisViews);
                communityRedisService.resetView(community.getId());
            }
        }
    }
}
