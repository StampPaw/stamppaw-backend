package org.example.stamppaw_backend.admin.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.admin.mission.dto.MissionCreateRequest;
import org.example.stamppaw_backend.admin.mission.dto.MissionResponse;
import org.example.stamppaw_backend.mission.entity.Mission;
import org.example.stamppaw_backend.mission.entity.UserMission;
import org.example.stamppaw_backend.mission.repository.MissionRepository;
import org.example.stamppaw_backend.mission.service.UserMissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/missions")
@RequiredArgsConstructor
public class AdminMissionController {

    private final MissionRepository missionRepository;
    private final UserMissionService userMissionService;

    @PostMapping("/new")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MissionResponse> createMission(
            @Valid @RequestBody MissionCreateRequest request
    ) {
        Mission mission = Mission.builder()
                .content(request.getContent())
                .reward(request.getReward())
                .build();

        Mission saved = missionRepository.save(mission);
        return ResponseEntity.ok(MissionResponse.fromEntity(saved));
    }

    @PostMapping("/assign")
    public UserMission assignMission(@RequestParam Long userId, @RequestParam Long missionId) {
        return userMissionService.assignMission(userId, missionId);
    }

}