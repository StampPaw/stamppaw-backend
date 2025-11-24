package org.example.stamppaw_backend.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.community.dto.request.CommentCreateRequest;
import org.example.stamppaw_backend.community.dto.response.CommentResponse;
import org.example.stamppaw_backend.community.service.CommentService;
import org.example.stamppaw_backend.user.service.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@Valid @RequestBody CommentCreateRequest request,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentService.createComment(request, userDetails.getUser().getId());
        return ResponseEntity.ok("댓글 생성 완료");
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<Page<CommentResponse>> getComments(@PathVariable Long communityId,
                                                             @PageableDefault(size = 10, sort = "registeredAt", direction = Sort.Direction.DESC)
                                                             Pageable pageable) {
        Page<CommentResponse> response = commentService.getComments(communityId, pageable);

        return ResponseEntity.ok(response);
    }
}
