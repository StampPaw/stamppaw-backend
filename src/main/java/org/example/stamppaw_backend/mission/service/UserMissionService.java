package org.example.stamppaw_backend.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.admin.mission.entity.Mission;
import org.example.stamppaw_backend.admin.mission.repository.MissionRepository;
import org.example.stamppaw_backend.common.exception.ErrorCode;
import org.example.stamppaw_backend.common.exception.StampPawException;
import org.example.stamppaw_backend.mission.dto.UserMissionDto;
import org.example.stamppaw_backend.mission.entity.UserMission;
import org.example.stamppaw_backend.mission.repository.UserMissionRepository;
import org.example.stamppaw_backend.point.entity.Point;
import org.example.stamppaw_backend.point.repository.PointRepository;
import org.example.stamppaw_backend.point.service.PointService;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.repository.UserRepository;
import org.example.stamppaw_backend.walk.entity.Walk;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserMissionService {

    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    public UserMission createUserMission(Long userId, Long missionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new StampPawException(ErrorCode.USER_NOT_FOUND));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new StampPawException(ErrorCode.MISSION_NOT_FOUND));

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .startDate(LocalDate.now())
                .status(false)
                .build();

        return userMissionRepository.save(userMission);
    }

    @Transactional(readOnly = true)
    public List<UserMissionDto> getUserMissions(Long userId) {
        return userMissionRepository.findByUserId(userId)
                .stream()
                .map(UserMissionDto::fromEntity)
                .toList();
    }

    // 관리자 or 유저가 직접 미션 완료
    public UserMissionDto completeMission(Long userMissionId) {

        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new StampPawException(ErrorCode.MISSION_NOT_FOUND));

        if (userMission.isStatus()) {
            throw new StampPawException(ErrorCode.MISSION_ALREADY_COMPLETED);
        }

        userMission.setStatus(true);
        userMissionRepository.save(userMission);

        return UserMissionDto.fromEntity(userMission);
    }

}
