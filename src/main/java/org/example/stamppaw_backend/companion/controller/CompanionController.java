package org.example.stamppaw_backend.companion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.companion.dto.request.CompanionCreateRequest;
import org.example.stamppaw_backend.companion.dto.response.CompanionResponse;
import org.example.stamppaw_backend.companion.service.CompanionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companion")
@RequiredArgsConstructor
public class CompanionController {
    private final CompanionService companionService;

    @PostMapping()
    public CompanionResponse createCompanion(@Valid @RequestBody CompanionCreateRequest request) {
        return companionService.createCompanion(request);
    }
}
