package com.alice.hotel.api.domain.admin.hotelRoom.web;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.domain.admin.hotelRoom.service.HotelRoomService;
import com.alice.hotel.api.dto.admin.hotelRoom.HotelRoomDTO;
import com.alice.hotel.api.dto.admin.hotelRoom.HotelRoomSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/a/hotel-rooms")
public class HotelRoomController {

    private final HotelRoomService hotelRoomService;

    @PostMapping("/list")
    public ApiResponse<Page<HotelRoomDTO>> list(@RequestBody HotelRoomSearchDTO searchDTO) {
        return new ApiResponse<>(ApiStatus.SUCCESS, hotelRoomService.search(searchDTO));
    }

    @GetMapping("/{roomId}")
    public ApiResponse<HotelRoomDTO> get(@PathVariable String roomId) {
        return new ApiResponse<>(ApiStatus.SUCCESS, hotelRoomService.get(roomId));
    }

    @PostMapping
    public ApiResponse<HotelRoomDTO> create(@RequestBody HotelRoomDTO dto) {
        return new ApiResponse<>(ApiStatus.CREATED, hotelRoomService.create(dto));
    }

    @PutMapping("/{roomId}")
    public ApiResponse<HotelRoomDTO> update(@PathVariable String roomId, @RequestBody HotelRoomDTO dto) {
        return new ApiResponse<>(ApiStatus.SUCCESS, hotelRoomService.update(roomId, dto));
    }

    @DeleteMapping("/{roomId}")
    public ApiResponse<Boolean> delete(@PathVariable String roomId) {
        hotelRoomService.delete(roomId);
        return new ApiResponse<>(ApiStatus.SUCCESS, true);
    }
}
