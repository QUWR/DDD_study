package org.example.ddd_study.application.port.in;

public record GetPublicRoomResult(
        String roomId,
        String title,
        int currentCount,
        int capacity,
        boolean isPrivate
) {
}
