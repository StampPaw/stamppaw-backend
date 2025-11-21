package org.example.stamppaw_backend.community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.stamppaw_backend.user.entity.User;

@Entity
@Table(name = "community")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User user;
}
