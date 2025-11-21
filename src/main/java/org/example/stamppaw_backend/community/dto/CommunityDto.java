package org.example.stamppaw_backend.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {
    private String title;
    private String content;
    private String image;
}
