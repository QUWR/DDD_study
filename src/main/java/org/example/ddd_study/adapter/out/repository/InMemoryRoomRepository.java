package org.example.ddd_study.adapter.out.repository;

import org.example.ddd_study.application.port.out.LoadRoomPort;
import org.example.ddd_study.application.port.out.SaveRoomPort;
import org.example.ddd_study.domain.game.entity.RoomSession;
import org.example.ddd_study.domain.game.vo.RoomId;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryRoomRepository implements LoadRoomPort, SaveRoomPort {

    private final Map<RoomId, RoomSession> store = new ConcurrentHashMap<>();

    @Override
    public Optional<RoomSession> loadRoom(RoomId roomId) {
        return Optional.ofNullable(store.get(roomId));
    }

    @Override
    public void saveRoom(RoomSession roomSession) {
        store.put(roomSession.getId(), roomSession);
    }
}
//todo: 레포지토리 수정