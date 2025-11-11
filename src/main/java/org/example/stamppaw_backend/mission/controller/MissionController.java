package org.example.stamppaw_backend.mission.controller;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.mission.entity.Mission;
import org.example.stamppaw_backend.mission.service.MissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public Optional<Mission> getMission(Long id) {
        return missionService.getMission(id);
    }

    @GetMapping("/all")
    public List<Mission> getAllMissions() {
        return missionService.getAllMissions();
    }
}