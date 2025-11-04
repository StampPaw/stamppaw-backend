package org.example.stamppaw_backend.companion.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.companion.dto.request.CompanionCreateRequest;
import org.example.stamppaw_backend.companion.dto.response.CompanionResponse;
import org.example.stamppaw_backend.companion.entity.Companion;
import org.example.stamppaw_backend.companion.repository.CompanionRepository;
import org.example.stamppaw_backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanionService {
    private final CompanionRepository companionRepository;

    public CompanionResponse createCompanion(CompanionCreateRequest request) {
        User user = User.builder()
                .id(1L)
                .build();
        Companion companion = companionRepository.save(
                Companion.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .user(user)
                        .build()
        );

        return CompanionResponse.fromEntity(companion);
    }

    @Transactional(readOnly = true)
    public Page<CompanionResponse> getAllCompanion(Pageable pageable) {
        Page<Companion> companions = companionRepository.findAll(pageable);
        return companions.map(CompanionResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public CompanionResponse getCompanion(Long postId) {
        Companion companion = companionRepository.findById(postId)
                .orElseThrow(() -> new StampPawException(ErrorCode.COMPANION_NOT_FOUND));

        return CompanionResponse.fromEntity(companion);
    }
}
