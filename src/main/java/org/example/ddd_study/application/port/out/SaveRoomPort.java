package org.example.ddd_study.application.port.out;

import org.example.ddd_study.domain.game.entity.RoomSession;

public interface SaveRoomPort {
    void saveRoom(RoomSession roomSession);
}
