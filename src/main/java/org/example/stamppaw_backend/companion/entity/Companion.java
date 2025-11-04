package org.example.stamppaw_backend.companion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.stamppaw_backend.common.BasicTimeEntity;
import org.example.stamppaw_backend.user.entity.User;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "companions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Companion extends BasicTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatus status;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "companion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanionApply> applies = new ArrayList<>();

    @Override
    public void setPrePersist() {
        super.setPrePersist();
        this.status = RecruitmentStatus.ONGOING;
    }

}