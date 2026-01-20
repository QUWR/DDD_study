package org.example.ddd_study.application.port.in;

import org.example.ddd_study.adapter.in.dto.RoomResponse;
import org.example.ddd_study.domain.game.vo.RoomId;

public interface GetRoomInfoUseCase {
    RoomResponse getRoomInfo(RoomId roomId);
}
