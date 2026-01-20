package org.example.ddd_study.application.port.in;

public interface CreateRoomUseCase {
    CreateRoomResult createRoom(CreateRoomCommand command);
}