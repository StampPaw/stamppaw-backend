// UserMissionService.java
package org.example.stamppaw_backend.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.point.entity.Point;
import org.example.stamppaw_backend.admin.mission.entity.Mission;
import org.example.stamppaw_backend.mission.entity.UserMission;
import org.example.stamppaw_backend.point.repository.PointRepository;
import org.example.stamppaw_backend.admin.mission.repository.MissionRepository;
import org.example.stamppaw_backend.mission.repository.UserMissionRepository;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMissionService {

    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Transactional
    public UserMission assignMission(Long userId, Long missionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .startDate(LocalDate.now())
                .status(false)
                .build();

        return userMissionRepository.save(userMission);
    }

    @Transactional
    public UserMission completeMission(Long userMissionId) {
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new RuntimeException("UserMission not found"));

        if (userMission.isStatus()) {
            throw new RuntimeException("Already completed");
        }

        userMission.setStatus(true);
        userMission.setEndDate(LocalDate.now());

        // 포인트 증가
        Point point = pointRepository.findByUserId(userMission.getUser().getId())
                .orElse(Point.builder()
                        .user(userMission.getUser())
                        .total(0)
                        .build());

        point.setTotal(point.getTotal() + userMission.getMission().getPoint());
        pointRepository.save(point);

        return userMissionRepository.save(userMission);
    }

    public List<UserMission> getUserMissions(Long userId) {
        return userMissionRepository.findByUserId(userId);
    }
}
