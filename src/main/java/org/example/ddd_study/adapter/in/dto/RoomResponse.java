package org.example.ddd_study.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.ddd_study.domain.game.entity.RoomSession;
import org.example.ddd_study.domain.game.enums.RoomStatus;

import java.time.Instant;

@Getter
@Builder
public class RoomResponse {
    private String roomId;
    private String title;
    private boolean isPrivate;
    private int capacity;
    private int currentCount;
    private Long hostUserId;
    private RoomStatus status;
    private Instant createdAt;

    public static RoomResponse from(RoomSession room) {
        return RoomResponse.builder()
                .roomId(room.getId().value())
                .title(room.getTitle().value())
                .isPrivate(room.isPrivate())
                .capacity(room.getCapacity())
                .currentCount(room.getPlayerCount())
                .hostUserId(room.getHostUserId().value())
                .status(room.getStatus())
                .createdAt(room.getCreatedAt())
                .build();
    }
}
