package org.example.stamppaw_backend.mission.repository;

import org.example.stamppaw_backend.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {

}