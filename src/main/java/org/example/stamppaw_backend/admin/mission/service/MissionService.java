package org.example.stamppaw_backend.admin.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.admin.mission.dto.MissionRequest;
import org.example.stamppaw_backend.admin.mission.dto.MissionResponse;
import org.example.stamppaw_backend.admin.mission.dto.MissionUpdateRequest;
import org.example.stamppaw_backend.admin.mission.entity.Mission;
import org.example.stamppaw_backend.admin.mission.repository.MissionRepository;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionResponse createMission(MissionRequest request) {
        Mission mission = Mission.builder()
                .content(request.getContent())
                .point(request.getPoint())
                .build();
        Mission saved = missionRepository.save(mission);
        return MissionResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<MissionResponse> getAllMissions() {
        return missionRepository.findAll()
                .stream()
                .map(MissionResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MissionResponse getMissionById(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new StampPawException(ErrorCode.MISSION_NOT_FOUND));
        return MissionResponse.fromEntity(mission);
    }

    public MissionResponse updateMission(Long id, MissionRequest request) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new StampPawException(ErrorCode.MISSION_NOT_FOUND));

        mission.setContent(request.getContent());
        mission.setPoint(request.getPoint());

        Mission updated = missionRepository.save(mission);
        return MissionResponse.fromEntity(updated);
    }

    public void deleteMission(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new StampPawException(ErrorCode.MISSION_NOT_FOUND));
        missionRepository.delete(mission);
    }
}