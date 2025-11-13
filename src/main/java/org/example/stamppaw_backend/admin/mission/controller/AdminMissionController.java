package org.example.stamppaw_backend.admin.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.admin.mission.dto.MissionRequest;
import org.example.stamppaw_backend.admin.mission.dto.MissionResponse;
import org.example.stamppaw_backend.admin.mission.service.MissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/missions")
@RequiredArgsConstructor
public class AdminMissionController {

    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<MissionResponse> createMission(
            @Valid @RequestBody MissionRequest request
    ) {
        return ResponseEntity.ok(missionService.createMission(request));
    }

    @GetMapping
    public ResponseEntity<List<MissionResponse>> getAllMissions() {
        return ResponseEntity.ok(missionService.getAllMissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionResponse> getMissionById(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.getMissionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissionResponse> updateMission(
            @PathVariable Long id,
            @Valid @RequestBody MissionRequest request
    ) {
        return ResponseEntity.ok(missionService.updateMission(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        missionService.deleteMission(id);
        return ResponseEntity.noContent().build();
    }
}