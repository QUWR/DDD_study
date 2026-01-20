package org.example.ddd_study.adapter.in.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.in.common.response.ApiResponse;
import org.example.ddd_study.adapter.in.dto.CreateRoomRequest;
import org.example.ddd_study.adapter.in.dto.CreateRoomResponse;
import org.example.ddd_study.adapter.in.dto.RoomSummaryResponse;
import org.example.ddd_study.application.port.in.CreateRoomUseCase;
import org.example.ddd_study.application.port.in.GetAllRoomsUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomUseCase createRoomUseCase;
    private final GetAllRoomsUseCase getAllRoomsUseCase;

    @PostMapping
    public ApiResponse<CreateRoomResponse> createRoom(@RequestBody @Valid CreateRoomRequest request
                                                      //todo:@Authentication 추가
                                                      ) {
        CreateRoomResponse response = createRoomUseCase.createRoom(request);
        return ApiResponse.success(response);
    }

    @GetMapping()
    public ApiResponse<List<RoomSummaryResponse>> getRoomInfo(
            //todo:@Authentication 추가
    ) {
        List<RoomSummaryResponse> response = getAllRoomsUseCase.getAllRooms();
        return ApiResponse.success(response);
    }
}
