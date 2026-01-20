package org.example.ddd_study.application.service;

import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.in.dto.CreateRoomRequest;
import org.example.ddd_study.adapter.in.dto.CreateRoomResponse;
import org.example.ddd_study.adapter.in.dto.RoomSummaryResponse;
import org.example.ddd_study.application.port.in.CreateRoomUseCase;
import org.example.ddd_study.application.port.in.GetPublicRoomsUseCase;
import org.example.ddd_study.application.port.out.RoomPort;
import org.example.ddd_study.domain.game.entity.RoomSession;
import org.example.ddd_study.domain.game.vo.GameUserId;
import org.example.ddd_study.domain.game.vo.RoomId;
import org.example.ddd_study.domain.game.vo.RoomTitle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService implements CreateRoomUseCase, GetPublicRoomsUseCase {

    private final RoomPort roomPort;

    @Override
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        RoomId roomId = RoomId.of(UUID.randomUUID().toString());//todo: 추후 openvidu 사용시 세션ID로 변경
        RoomTitle roomTitle = RoomTitle.of(request.getTitle());
        GameUserId hostUserId = GameUserId.of(request.getHostUserId());

        RoomSession roomSession = RoomSession.create(
                roomId,
                roomTitle,
                request.isPrivate(),
                request.getCapacity(),
                hostUserId,
                request.getHostDisplayName()
        );

        roomPort.saveRoom(roomSession);

        return new CreateRoomResponse(roomId.value(), roomSession.getInviteCode());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomSummaryResponse> getPublicRooms() {

        List<RoomSession> sessions = roomPort.loadPublicRooms();

        List<RoomSummaryResponse> summaries = sessions.stream()
                .map(room -> RoomSummaryResponse.builder()
                        .roomId(room.getId().value())
                        .title(room.getTitle().value())
                        .currentCount(room.getPlayerCount())
                        .capacity(room.getCapacity())
                        .isPrivate(room.isPrivate())
                        .build())
                .collect(Collectors.toList());


        return summaries;
    }
}
