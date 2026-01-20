package org.example.ddd_study.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    @NotBlank(message = "방 제목은 필수입니다.")
    private String title;

    @Min(value = 6, message = "최소 인원은 6명입니다.")
    @Max(value = 8, message = "최대 인원은 8명입니다.")
    private int capacity;

    private boolean isPrivate;
    private Long hostUserId;
    private String hostDisplayName;
}
