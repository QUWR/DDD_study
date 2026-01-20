package org.example.ddd_study.domain.game.vo;

import java.util.UUID;

public record PrivateGame(
        boolean isPrivate,
        String inviteCode
) {
    // 1. 컴팩트 생성자: 유효성 검증 및 코드 자동 생성
    public PrivateGame {
        if (isPrivate) {
            // 비공개 방인데 코드가 없으면 자동 생성
            if (inviteCode == null || inviteCode.isBlank()) {
                inviteCode = UUID.randomUUID().toString().substring(0, 8);
            }
        } else {
            // 공개 방이면 코드는 항상 null
            inviteCode = null;
        }
    }

    // 2. 정적 팩토리 메서드 (사용 편의성)
    public static PrivateGame open() {
        return new PrivateGame(false, null);
    }

    public static PrivateGame closed() {
        return new PrivateGame(true, null); // 내부에서 코드 자동 생성됨
    }

    // 이미 있는 코드로 복원할 때 (DB 로드 등)
    public static PrivateGame of(boolean isPrivate, String inviteCode) {
        return new PrivateGame(isPrivate, inviteCode);
    }
}