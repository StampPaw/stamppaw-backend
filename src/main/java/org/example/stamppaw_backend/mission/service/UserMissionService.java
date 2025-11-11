// UserMissionService.java
package org.example.stamppaw_backend.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.mission.entity.Exp;
import org.example.stamppaw_backend.mission.entity.ExpType;
import org.example.stamppaw_backend.mission.entity.Mission;
import org.example.stamppaw_backend.mission.entity.UserMission;
import org.example.stamppaw_backend.mission.repository.ExpRepository;
import org.example.stamppaw_backend.mission.repository.MissionRepository;
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
    private final ExpRepository expRepository;

    // ✅ 유저에게 미션 부여
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

    // ✅ 미션 완료 처리
    @Transactional
    public UserMission completeMission(Long userMissionId) {
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new RuntimeException("UserMission not found"));

        if (userMission.isStatus()) {
            throw new RuntimeException("Already completed");
        }

        userMission.setStatus(true);
        userMission.setEndDate(LocalDate.now());

        // 경험치 증가 로직
        Exp exp = expRepository.findByUserIdAndType(userMission.getUser().getId(), ExpType.POINT)
                .orElse(Exp.builder()
                        .user(userMission.getUser())
                        .type(ExpType.POINT)
                        .total(0)
                        .build());

        exp.setTotal(exp.getTotal() + userMission.getMission().getReward());
        expRepository.save(exp);

        return userMissionRepository.save(userMission);
    }

    public List<UserMission> getUserMissions(Long userId) {
        return userMissionRepository.findByUserId(userId);
    }
}
