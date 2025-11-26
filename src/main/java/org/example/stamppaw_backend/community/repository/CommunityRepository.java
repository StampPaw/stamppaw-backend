package org.example.stamppaw_backend.community.repository;

import org.example.stamppaw_backend.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
