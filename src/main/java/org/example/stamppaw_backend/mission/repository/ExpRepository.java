package org.example.stamppaw_backend.mission.repository;

import org.example.stamppaw_backend.mission.entity.Exp;
import org.example.stamppaw_backend.mission.entity.ExpType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpRepository extends JpaRepository<Exp, Long> {
    Optional<Exp> findByUserIdAndType(Long userId, ExpType type);
}