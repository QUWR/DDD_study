package org.example.ddd_study.application.port.in;

public record CreateRoomResult(
        String roomId,
        String inviteCode
) {
}
