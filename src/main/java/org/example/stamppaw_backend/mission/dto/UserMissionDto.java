package org.example.stamppaw_backend.mission.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMissionDto {
    private Long id;
    private Long userId;
    private Long missionId;
    private String missionContent;
    private int rewardPoint;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean status;
}