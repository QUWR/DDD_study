package org.example.ddd_study.application.port.in;



import java.util.List;

public interface GetPublicRoomsUseCase {

    List<GetPublicRoomResult> getPublicRooms(int page, int size);
}
