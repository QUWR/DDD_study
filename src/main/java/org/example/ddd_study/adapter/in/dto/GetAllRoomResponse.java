package org.example.ddd_study.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetAllRoomResponse {
    private String roomId;
    private String title;
    private int currentCount;
    private int capacity;
    private boolean isPrivate;

}
