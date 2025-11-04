package org.example.stamppaw_backend.companion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.companion.dto.request.CompanionCreateRequest;
import org.example.stamppaw_backend.companion.dto.response.CompanionResponse;
import org.example.stamppaw_backend.companion.service.CompanionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companion")
@RequiredArgsConstructor
public class CompanionController {
    private final CompanionService companionService;

    @PostMapping
    public CompanionResponse createCompanion(@Valid CompanionCreateRequest request) {
        return companionService.createCompanion(request);
    }

    @GetMapping
    public ResponseEntity<Page<CompanionResponse>> getAllCompanion(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(companionService.getAllCompanion(pageable));
    }

    @GetMapping("/{postId}")
    public CompanionResponse getCompanion(@PathVariable Long postId) {
        return companionService.getCompanion(postId);
    }
}
