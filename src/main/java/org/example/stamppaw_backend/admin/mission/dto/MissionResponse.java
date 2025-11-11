package org.example.stamppaw_backend.admin.mission.dto;

import lombok.*;
import org.example.stamppaw_backend.mission.entity.Mission;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionResponse {
    private Long id;
    private String content;
    private int point;

    public static MissionResponse fromEntity(Mission mission) {
        return MissionResponse.builder()
                .id(mission.getId())
                .content(mission.getContent())
                .point(mission.getReward())
                .build();
    }
}