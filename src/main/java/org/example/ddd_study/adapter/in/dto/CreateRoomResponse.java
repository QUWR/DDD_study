package org.example.ddd_study.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRoomResponse {
    private String roomId;
    private String inviteCode;
}
