package org.example.ddd_study.adapter.out.repository;

import org.example.ddd_study.adapter.out.entity.RoomRedisEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpringDataRoomRedisRepository extends CrudRepository<RoomRedisEntity, String> {
    Optional<RoomRedisEntity> findByInviteCode(String inviteCode);
}
