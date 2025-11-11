package org.example.stamppaw_backend.companion.repository;

import org.example.stamppaw_backend.companion.entity.Companion;
import org.example.stamppaw_backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanionRepository extends JpaRepository<Companion, Long> {
    Page<Companion> findAllByUser(Pageable pageable, User user);
}
