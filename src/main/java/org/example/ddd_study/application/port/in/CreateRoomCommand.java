package org.example.ddd_study.application.port.in;

public record CreateRoomCommand(
        String title,
        int capacity,
        boolean isPrivate,
        Long hostUserId,
        String hostDisplayName
) {
    // 필요한 경우 내부 검증 로직(Validation)을 생성자에 추가할 수 있습니다.
    public CreateRoomCommand {
        if (capacity < 6 || capacity > 8) {
            throw new IllegalArgumentException("인원은 6~8명 사이여야 합니다.");
        }
    }
}
