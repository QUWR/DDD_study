package org.example.ddd_study.application.port.in;

import org.example.ddd_study.adapter.in.dto.RoomSummaryResponse;

import java.util.List;

public interface GetAllRoomsUseCase {

    List<RoomSummaryResponse> getAllRooms();
}
