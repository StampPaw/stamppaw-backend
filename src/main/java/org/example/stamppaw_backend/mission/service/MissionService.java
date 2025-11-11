package org.example.stamppaw_backend.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.mission.entity.Mission;
import org.example.stamppaw_backend.mission.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;



    public Optional<Mission> getMission(Long id) {
        return missionRepository.findById(id);
    }

    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

}