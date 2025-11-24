package org.example.stamppaw_backend.community.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.community.dto.request.CommentCreateRequest;
import org.example.stamppaw_backend.community.dto.response.CommentResponse;
import org.example.stamppaw_backend.community.entity.Comment;
import org.example.stamppaw_backend.community.entity.Community;
import org.example.stamppaw_backend.community.repository.CommentRepository;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityService communityService;
    private final UserService userService;

    public void createComment(CommentCreateRequest request, Long userId) {
        User user = userService.getUserOrException(userId);
        Community community = communityService.getCommunityOrException(request.getCommunityId());

        Comment parent = null;
        if(request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new StampPawException(ErrorCode.PARENT_COMMENT_NOT_FOUND));
        }

        commentRepository.save(Comment.builder()
                .content(request.getContent())
                .community(community)
                .user(user)
                .parent(parent)
                .build());
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(Long communityId, Pageable pageable) {
        Page<Comment> parent = commentRepository.findByCommunityIdAndParentIsNull(communityId, pageable);
        Page<CommentResponse> responsePage = parent.map(CommentResponse::fromEntity);

        return responsePage;
    }
}
