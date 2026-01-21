package org.example.ddd_study.adapter.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.ddd_study.domain.game.enums.RoomStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("room")
public class RoomRedisEntity {

    @Id
    private String id;
    private String title;
    private boolean isPrivate;
    @Indexed
    private String inviteCode;
    private int capacity;
    private Long hostUserId;
    private RoomStatus status;
    private long version;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant startedAt;
    private Instant endedAt;
    
    // Complex objects stored as JSON strings
    private String playersJson;
    private String gameStateJson;
}
