package org.example.ddd_study.adapter.out.repository;

import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.out.entity.RoomRedisEntity;
import org.example.ddd_study.adapter.out.mapper.RoomMapper;
import org.example.ddd_study.application.port.out.RoomPort;
import org.example.ddd_study.domain.game.entity.RoomSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * adapter 없이 한번에 구현한 방식
 */
@Repository
@RequiredArgsConstructor
public class RedisRoomRepository implements RoomPort {

    private static final String PUBLIC_ROOMS_KEY = "room:public";

    private final RedisTemplate<String, Object> redisTemplate;
    private final RoomMapper roomMapper;


    @Override
    public void saveRoom(RoomSession roomSession) {
        RoomRedisEntity entity = roomMapper.toEntity(roomSession);
        

        String key = "room:" + entity.getId();
        redisTemplate.opsForValue().set(key, entity); // This might serialize the whole object.
        
        saveEntity(entity);

        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();

        if (!roomSession.isPrivate()) {
            double score = roomSession.getCreatedAt().toEpochMilli();
            zSetOps.add(PUBLIC_ROOMS_KEY, roomSession.getId().value(), score);
        } else {
            zSetOps.remove(PUBLIC_ROOMS_KEY, roomSession.getId().value());
        }
    }

    @Override
    public List<RoomSession> loadPublicRooms(int page, int size) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        
        int start = page * size;
        int end = start + size - 1;


        Set<Object> roomIds = zSetOps.reverseRange(PUBLIC_ROOMS_KEY, start, end);

        if (roomIds == null || roomIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> keys = roomIds.stream()
                .map(id -> "room:" + id)
                .collect(Collectors.toList());

        List<Object> results = redisTemplate.opsForValue().multiGet(keys);

        if (results == null) {
            return Collections.emptyList();
        }

        return results.stream()
                .filter(Objects::nonNull)
                .map(obj -> (RoomRedisEntity) obj)
                .map(roomMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    private void saveEntity(RoomRedisEntity entity) {
        redisTemplate.opsForValue().set("room:" + entity.getId(), entity);
    }
}
