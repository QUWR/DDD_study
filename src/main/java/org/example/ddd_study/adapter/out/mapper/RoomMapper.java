package org.example.ddd_study.adapter.out.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ddd_study.adapter.out.entity.RoomRedisEntity;
import org.example.ddd_study.domain.game.entity.PlayerState;
import org.example.ddd_study.domain.game.entity.RoomSession;
import org.example.ddd_study.domain.game.vo.GameState;
import org.example.ddd_study.domain.game.vo.GameUserId;
import org.example.ddd_study.domain.game.vo.RoomId;
import org.example.ddd_study.domain.game.vo.RoomTitle;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final ObjectMapper objectMapper;

    public RoomSession toDomain(RoomRedisEntity entity) {
        try {
            Map<GameUserId, PlayerState> players = objectMapper.readValue(
                    entity.getPlayersJson(),
                    new TypeReference<Map<GameUserId, PlayerState>>() {}
            );
            
            GameState gameState = objectMapper.readValue(
                    entity.getGameStateJson(),
                    GameState.class
            );

            return RoomSession.reconstitute(
                    RoomId.of(entity.getId()),
                    RoomTitle.of(entity.getTitle()),
                    entity.isPrivate(),
                    entity.getInviteCode(),
                    entity.getCapacity(),
                    GameUserId.of(entity.getHostUserId()),
                    entity.getStatus(),
                    entity.getVersion(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt(),
                    entity.getStartedAt(),
                    entity.getEndedAt(),
                    players,
                    gameState
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize RoomSession from Redis", e);
        }
    }

    public RoomRedisEntity toEntity(RoomSession domain) {
        try {
            String playersJson = objectMapper.writeValueAsString(domain.getPlayers());
            String gameStateJson = objectMapper.writeValueAsString(domain.getGameState());

            return RoomRedisEntity.builder()
                    .id(domain.getId().value())
                    .title(domain.getTitle().value())
                    .isPrivate(domain.isPrivate())
                    .inviteCode(domain.getInviteCode())
                    .capacity(domain.getCapacity())
                    .hostUserId(domain.getHostUserId().value())
                    .status(domain.getStatus())
                    .version(domain.getVersion())
                    .createdAt(domain.getCreatedAt())
                    .updatedAt(domain.getUpdatedAt())
                    .startedAt(domain.getStartedAt())
                    .endedAt(domain.getEndedAt())
                    .playersJson(playersJson)
                    .gameStateJson(gameStateJson)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize RoomSession to Redis", e);
        }
    }
}
