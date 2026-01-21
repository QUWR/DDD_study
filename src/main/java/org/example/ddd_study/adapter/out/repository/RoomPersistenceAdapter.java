package org.example.ddd_study.adapter.out.repository;

import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.out.entity.RoomRedisEntity;
import org.example.ddd_study.adapter.out.mapper.RoomMapper;
import org.example.ddd_study.application.port.out.RoomPort;
import org.example.ddd_study.domain.game.entity.RoomSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class RoomPersistenceAdapter implements RoomPort {

    private static final String PUBLIC_ROOMS_KEY = "room:public";

    private final SpringDataRoomRedisRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RoomMapper roomMapper;

    @Override
    public void saveRoom(RoomSession roomSession) {
        // 1. 도메인 객체를 Redis 엔티티로 변환
        RoomRedisEntity entity = roomMapper.toEntity(roomSession);

        // 2. 기본 데이터 저장 (Hash)
        repository.save(entity);

        // 3. 공개 방 목록 인덱스 관리 (Sorted Set)
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        if (!roomSession.isPrivate()) {
            // 생성 시간을 점수로 하여 정렬 (최신순 조회를 위해)
            double score = roomSession.getCreatedAt().toEpochMilli();
            zSetOps.add(PUBLIC_ROOMS_KEY, roomSession.getId().value(), score);
        } else {
            // 비공개로 전환되었거나 비공개 방인 경우 목록에서 제거
            zSetOps.remove(PUBLIC_ROOMS_KEY, roomSession.getId().value());
        }
    }

    @Override
    public List<RoomSession> loadPublicRooms(int page, int size) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();

        int start = page * size;
        int end = start + size - 1;

        // 1. ZSet에서 해당 페이지의 Room ID 목록 조회 (최신순: Reverse Range)
        Set<Object> roomIds = zSetOps.reverseRange(PUBLIC_ROOMS_KEY, start, end);

        if (roomIds == null || roomIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. ID 목록으로 실제 데이터 조회 (MGET 대신 Repository의 findAllById 사용 가능)
        // findAllById는 내부적으로 MGET 등을 최적화하여 사용할 수 있음
        List<String> stringIds = roomIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        Iterable<RoomRedisEntity> entities = repository.findAllById(stringIds);

        // 3. 엔티티를 도메인 객체로 변환하여 반환
        return StreamSupport.stream(entities.spliterator(), false)
                .filter(Objects::nonNull)
                .map(roomMapper::toDomain)
                .collect(Collectors.toList());
    }
}
