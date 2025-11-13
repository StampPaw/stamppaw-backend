package org.example.stamppaw_backend.mission.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionDto {
    private Long id;
    private String content;
    private int point;
}