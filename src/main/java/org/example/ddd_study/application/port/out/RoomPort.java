package org.example.ddd_study.application.port.out;

import org.example.ddd_study.domain.game.entity.RoomSession;
import org.example.ddd_study.domain.game.vo.RoomId;

import java.util.Optional;

public interface RoomPort {

    Optional<RoomSession> loadRoom(RoomId roomId);
    void saveRoom(RoomSession roomSession);
}
