package org.example.stamppaw_backend.companion.repository;

import org.example.stamppaw_backend.companion.entity.Companion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanionRepository extends JpaRepository<Companion, Long> {
}
