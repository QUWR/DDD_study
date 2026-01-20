package org.example.ddd_study.application.service;

import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.in.dto.CreateRoomRequest;
import org.example.ddd_study.adapter.in.dto.CreateRoomResponse;
import org.example.ddd_study.adapter.in.dto.RoomResponse;
import org.example.ddd_study.application.exception.ApplicationException;
import org.example.ddd_study.application.exception.ExceptionType;
import org.example.ddd_study.application.port.in.CreateRoomUseCase;
import org.example.ddd_study.application.port.in.GetRoomInfoUseCase;
import org.example.ddd_study.application.port.out.RoomPort;
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

    private final RoomPort roomPort;

    @Override
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        RoomId roomId = RoomId.of(UUID.randomUUID().toString());//todo: 추후 opnvidu 사용시 세션ID로 변경
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

        return new CreateRoomResponse(roomId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomInfo(RoomId roomId) {
        RoomSession roomSession = roomPort.loadRoom(roomId)
                .orElseThrow(() -> ApplicationException.of(ExceptionType.ROOM_NOT_FOUND));

        return RoomResponse.from(roomSession);
    }
}
