package org.example.ddd_study.domain.game.vo;

import java.util.Objects;

public record GameUserId(Long value) {

    public GameUserId {
        Objects.requireNonNull(value, "GameUserId value must not be null");
    }

    public static GameUserId of(Long value) {
        return new GameUserId(value);
    }
}