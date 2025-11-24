package org.example.stamppaw_backend.parttime.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.stamppaw_backend.parttime.entity.PartTime;

@Data
@Builder
public class PartTimeResponse {
    private Long id;
    private String title;
    private String content;
    private String image;
    private String status;

    public static PartTimeResponse fromEntity(PartTime partTime) {
        return PartTimeResponse.builder()
            .id(partTime.getId())
            .title(partTime.getTitle())
            .content(partTime.getContent())
            .image(partTime.getImageUrl())
            .status(partTime.getStatus().toString())
            .build();
    }
}
