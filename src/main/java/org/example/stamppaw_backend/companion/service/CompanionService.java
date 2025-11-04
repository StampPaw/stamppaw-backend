package org.example.stamppaw_backend.companion.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.companion.dto.request.CompanionCreateRequest;
import org.example.stamppaw_backend.companion.dto.response.CompanionResponse;
import org.example.stamppaw_backend.companion.entity.Companion;
import org.example.stamppaw_backend.companion.repository.CompanionRepository;
import org.example.stamppaw_backend.user.entity.User;
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
}
