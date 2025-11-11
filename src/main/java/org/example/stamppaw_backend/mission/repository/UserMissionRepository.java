package org.example.stamppaw_backend.mission.repository;

import org.example.stamppaw_backend.mission.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    List<UserMission> findByUserId(Long userId);
}