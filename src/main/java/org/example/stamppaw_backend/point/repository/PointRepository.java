package org.example.stamppaw_backend.point.repository;

import org.example.stamppaw_backend.point.entity.Point;
import org.example.stamppaw_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByUser(User user);

    Optional<Point> findTopByUserOrderByIdDesc(User user);

    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Point p WHERE p.user = :user")
    int getTotalPointsByUser(User user);


}