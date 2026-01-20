package org.example.ddd_study.application.port.in;

import org.example.ddd_study.adapter.in.dto.CreateRoomRequest;
import org.example.ddd_study.adapter.in.dto.CreateRoomResponse;

public interface CreateRoomUseCase {
    CreateRoomResponse createRoom(CreateRoomRequest request);
}
