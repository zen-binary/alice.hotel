package com.alice.hotel.api.domain.admin.hotelRoom.service;

import com.alice.hotel.api.dto.admin.hotelRoom.HotelRoomDTO;
import com.alice.hotel.api.dto.admin.hotelRoom.HotelRoomSearchDTO;
import org.springframework.data.domain.Page;

public interface HotelRoomService {

    Page<HotelRoomDTO> search(HotelRoomSearchDTO searchDTO);

    HotelRoomDTO get(String roomId);

    HotelRoomDTO create(HotelRoomDTO dto);

    HotelRoomDTO update(String roomId, HotelRoomDTO dto);

    void delete(String roomId);
}
