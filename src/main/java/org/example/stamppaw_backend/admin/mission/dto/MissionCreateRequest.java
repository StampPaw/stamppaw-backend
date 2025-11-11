package org.example.stamppaw_backend.admin.mission.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionCreateRequest {

    @NotBlank(message = "미션 내용을 입력해주세요.")
    private String content;

    @NotNull(message = "보상 수량을 입력해주세요.")
    @Min(value = 1, message = "보상은 최소 1 이상이어야 합니다.")
    private Integer reward;
}