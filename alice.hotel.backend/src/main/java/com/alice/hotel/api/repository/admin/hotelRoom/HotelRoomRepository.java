package com.alice.hotel.api.repository.admin.hotelRoom;

import com.alice.hotel.api.entity.admin.hotelRoom.HotelRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRoomRepository extends JpaRepository<HotelRoomEntity, String> {

    @Query("""
            SELECT r FROM HotelRoomEntity r
            WHERE r.delYn = 'N'
              AND (:keyword IS NULL OR :keyword = ''
                   OR LOWER(r.roomCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(r.roomName) LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:roomType IS NULL OR :roomType = '' OR r.roomType = :roomType)
              AND (:status IS NULL OR :status = '' OR r.status = :status)
            """)
    Page<HotelRoomEntity> search(@Param("keyword") String keyword,
                                 @Param("roomType") String roomType,
                                 @Param("status") String status,
                                 Pageable pageable);

    Optional<HotelRoomEntity> findByRoomIdAndDelYn(String roomId, String delYn);

    boolean existsByRoomCodeAndDelYn(String roomCode, String delYn);

    boolean existsByRoomCodeAndDelYnAndRoomIdNot(String roomCode, String delYn, String roomId);
}
