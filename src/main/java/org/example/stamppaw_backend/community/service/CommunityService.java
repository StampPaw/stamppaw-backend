package org.example.stamppaw_backend.community.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.S3Service;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.community.dto.CommunityDto;
import org.example.stamppaw_backend.community.dto.request.CommunityCreateRequest;
import org.example.stamppaw_backend.community.dto.request.CommunityModifyRequest;
import org.example.stamppaw_backend.community.dto.response.CommunityResponse;
import org.example.stamppaw_backend.community.entity.Community;
import org.example.stamppaw_backend.community.repository.CommunityRepository;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserService userService;
    private final S3Service s3Service;
    private final CommunityRedisService communityRedisService;

    public void createCommunity(CommunityCreateRequest request, Long userId) {
        User user = userService.getUserOrException(userId);
        communityRepository.save(Community.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImage() != null ? s3Service.uploadFileAndGetUrl(request.getImage()) : null)
                .user(user)
                .build());
    }

    @Transactional(readOnly = true)
    public Page<CommunityResponse> getCommunities(Pageable pageable) {
        Page<Community> communities = communityRepository.findAll(pageable);
        return communities.map(CommunityResponse::from);
    }

    public CommunityResponse getCommunity(Long id, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();

        communityRedisService.increaseView(id, sessionId);

        Community community = getCommunityOrException(id);
        Long redisViews = communityRedisService.getViewCount(id);
        Long total = community.getViews() + redisViews;
        return CommunityResponse.fromEntity(community, total);
    }

    public void modifyCommunity(Long id, CommunityModifyRequest request, Long userId) {
        User user = userService.getUserOrException(userId);
        Community community = getCommunityOrException(id);
        verifyUser(user, community);
        String imageUrl = request.getImage() != null ? s3Service.uploadFileAndGetUrl(request.getImage()) : null;

        community.updateCommunity(CommunityDto.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .image(imageUrl)
                .build());
    }

    public Community getCommunityOrException(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new StampPawException(ErrorCode.COMMUNITY_NOT_FOUND));
    }

    private void verifyUser(User user, Community community) {
        if(!community.getUser().equals(user)) {
            throw new StampPawException(ErrorCode.FORBIDDEN_ACCESS);
        }
    }

}
