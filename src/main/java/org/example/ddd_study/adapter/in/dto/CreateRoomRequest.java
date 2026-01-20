package org.example.ddd_study.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    private String title;
    private int capacity;
    private boolean isPrivate;
    private String password;
    private Long hostUserId;
    private String hostDisplayName;
}
