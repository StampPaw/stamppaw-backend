package org.example.stamppaw_backend.companion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.stamppaw_backend.companion.entity.Companion;
import org.example.stamppaw_backend.companion.entity.RecruitmentStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanionResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;
    private RecruitmentStatus status;

    public static CompanionResponse fromEntity(Companion companion) {
        return CompanionResponse.builder()
                .id(companion.getId())
                .title(companion.getTitle())
                .content(companion.getContent())
                .registeredAt(companion.getRegisteredAt())
                .modifiedAt(companion.getModifiedAt())
                .status(companion.getStatus())
                .build();
    }
}
