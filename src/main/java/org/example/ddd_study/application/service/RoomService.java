package org.example.ddd_study.application.service;

import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.in.dto.CreateRoomRequest;
import org.example.ddd_study.adapter.in.dto.CreateRoomResponse;
import org.example.ddd_study.adapter.in.dto.RoomResponse;
import org.example.ddd_study.application.exception.ApplicationException;
import org.example.ddd_study.application.exception.ExceptionType;
import org.example.ddd_study.application.port.in.CreateRoomUseCase;
import org.example.ddd_study.application.port.in.GetRoomInfoUseCase;
import org.example.ddd_study.application.port.out.LoadRoomPort;
import org.example.ddd_study.application.port.out.SaveRoomPort;
import org.example.ddd_study.domain.game.entity.RoomSession;
import org.example.ddd_study.domain.game.vo.GameUserId;
import org.example.ddd_study.domain.game.vo.RoomId;
import org.example.ddd_study.domain.game.vo.RoomTitle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService implements CreateRoomUseCase, GetRoomInfoUseCase {

    private final LoadRoomPort loadRoomPort;
    private final SaveRoomPort saveRoomPort;

    @Override
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        RoomId roomId = RoomId.of(UUID.randomUUID().toString());
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

        saveRoomPort.saveRoom(roomSession);

        return new CreateRoomResponse(roomId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomInfo(RoomId roomId) {
        RoomSession roomSession = loadRoomPort.loadRoom(roomId)
                .orElseThrow(() -> ApplicationException.of(ExceptionType.ROOM_NOT_FOUND));

        return RoomResponse.from(roomSession);
    }
}
