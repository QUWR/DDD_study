package org.example.ddd_study.adapter.in.controller;

import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.in.common.response.ApiResponse;
import org.example.ddd_study.adapter.in.dto.CreateRoomRequest;
import org.example.ddd_study.adapter.in.dto.CreateRoomResponse;
import org.example.ddd_study.adapter.in.dto.RoomResponse;
import org.example.ddd_study.application.port.in.CreateRoomUseCase;
import org.example.ddd_study.application.port.in.GetRoomInfoUseCase;
import org.example.ddd_study.domain.game.vo.RoomId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomUseCase createRoomUseCase;
    private final GetRoomInfoUseCase getRoomInfoUseCase;

    @PostMapping
    public ApiResponse<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest request) {
        CreateRoomResponse response = createRoomUseCase.createRoom(request);
        return ApiResponse.success(response);
    }

    @GetMapping("/{roomId}")
    public ApiResponse<RoomResponse> getRoomInfo(@PathVariable String roomId) {
        RoomResponse response = getRoomInfoUseCase.getRoomInfo(RoomId.of(roomId));
        return ApiResponse.success(response);
    }
}
