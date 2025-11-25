package org.example.stamppaw_backend.community.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityModifyRequest {
    @NotNull(message = "제목은 필수 값입니다.")
    private String title;

    @NotNull(message = "내용은 필수 값입니다.")
    private String content;

    private MultipartFile image;
}
