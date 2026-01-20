package org.example.ddd_study.adapter.in.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.in.common.response.ApiResponse;
import org.example.ddd_study.adapter.in.dto.CreateRoomRequest;
import org.example.ddd_study.adapter.in.dto.CreateRoomResponse;
import org.example.ddd_study.adapter.in.dto.RoomSummaryResponse;
import org.example.ddd_study.application.port.in.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomUseCase createRoomUseCase;
    private final GetPublicRoomsUseCase getAllRoomsUseCase;

    @PostMapping
    public ApiResponse<CreateRoomResponse> createRoom(@RequestBody @Valid CreateRoomRequest request
                                                      //todo:@Authentication 추가
                                                      ) {
        CreateRoomCommand command = new CreateRoomCommand(
                request.getTitle(),
                request.getCapacity(),
                request.isPrivate(),
                request.getHostUserId(),
                request.getHostDisplayName()
        );
        CreateRoomResult result = createRoomUseCase.createRoom(command);
        CreateRoomResponse response = new CreateRoomResponse(
                result.roomId(),
                result.inviteCode()
        );
        return ApiResponse.success(response);
    }

    @GetMapping()
    public ApiResponse<List<RoomSummaryResponse>> getRoomInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
            //todo:@Authentication 추가
    ) {
        List<GetPublicRoomResult> results = getAllRoomsUseCase.getPublicRooms(page, size);
        List<RoomSummaryResponse> response = results.stream()
                .map(result -> RoomSummaryResponse.builder()
                        .roomId(result.roomId())
                        .title(result.title())
                        .currentCount(result.currentCount())
                        .capacity(result.capacity())
                        .isPrivate(result.isPrivate())
                        .build())
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }
}
