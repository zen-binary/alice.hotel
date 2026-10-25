package com.alice.hotel.api.domain.admin.hotelRoom.impl;

import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.common.Constants;
import com.alice.hotel.api.config.security.handler.CustomException;
import com.alice.hotel.api.domain.admin.hotelRoom.service.HotelRoomService;
import com.alice.hotel.api.domain.common.service.CommonService;
import com.alice.hotel.api.dto.admin.hotelRoom.HotelRoomDTO;
import com.alice.hotel.api.dto.admin.hotelRoom.HotelRoomSearchDTO;
import com.alice.hotel.api.entity.admin.hotelRoom.HotelRoomEntity;
import com.alice.hotel.api.repository.admin.hotelRoom.HotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelRoomServiceImpl implements HotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;
    private final ModelMapper modelMapper;
    private final CommonService commonService;

    @Override
    @Transactional(readOnly = true)
    public Page<HotelRoomDTO> search(HotelRoomSearchDTO searchDTO) {
        int page = Math.max(searchDTO.getPage(), 1) - 1;
        int size = searchDTO.getSize() <= 0 ? 10 : searchDTO.getSize();
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate"));
        return hotelRoomRepository
                .search(searchDTO.getKeyword(), searchDTO.getRoomType(), searchDTO.getStatus(), pageable)
                .map(entity -> modelMapper.map(entity, HotelRoomDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public HotelRoomDTO get(String roomId) {
        HotelRoomEntity entity = hotelRoomRepository.findByRoomIdAndDelYn(roomId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        return modelMapper.map(entity, HotelRoomDTO.class);
    }

    @Override
    @Transactional
    public HotelRoomDTO create(HotelRoomDTO dto) {
        validate(dto, null);
        String userId = commonService.getFullUserInfo().getUserId();
        HotelRoomEntity entity = modelMapper.map(dto, HotelRoomEntity.class);
        entity.setRoomId("ROOM" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
        entity.setDelYn(Constants.STATE_N);
        entity.setRegId(userId);
        entity.setRegDate(LocalDateTime.now());
        if (StringUtils.isBlank(entity.getStatus())) {
            entity.setStatus("AVAILABLE");
        }
        return modelMapper.map(hotelRoomRepository.save(entity), HotelRoomDTO.class);
    }

    @Override
    @Transactional
    public HotelRoomDTO update(String roomId, HotelRoomDTO dto) {
        HotelRoomEntity entity = hotelRoomRepository.findByRoomIdAndDelYn(roomId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        validate(dto, roomId);
        String userId = commonService.getFullUserInfo().getUserId();
        entity.setRoomCode(dto.getRoomCode());
        entity.setRoomName(dto.getRoomName());
        entity.setRoomType(dto.getRoomType());
        entity.setPrice(dto.getPrice());
        entity.setCapacity(dto.getCapacity());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setModId(userId);
        entity.setModDate(LocalDateTime.now());
        return modelMapper.map(hotelRoomRepository.save(entity), HotelRoomDTO.class);
    }

    @Override
    @Transactional
    public void delete(String roomId) {
        HotelRoomEntity entity = hotelRoomRepository.findByRoomIdAndDelYn(roomId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        entity.setDelYn(Constants.STATE_Y);
        entity.setModId(commonService.getFullUserInfo().getUserId());
        entity.setModDate(LocalDateTime.now());
        hotelRoomRepository.save(entity);
    }

    private void validate(HotelRoomDTO dto, String roomId) {
        if (StringUtils.isBlank(dto.getRoomCode()) || StringUtils.isBlank(dto.getRoomName())
                || StringUtils.isBlank(dto.getRoomType()) || dto.getPrice() == null || dto.getCapacity() == null) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
        boolean duplicated = roomId == null
                ? hotelRoomRepository.existsByRoomCodeAndDelYn(dto.getRoomCode(), Constants.STATE_N)
                : hotelRoomRepository.existsByRoomCodeAndDelYnAndRoomIdNot(dto.getRoomCode(), Constants.STATE_N, roomId);
        if (duplicated) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
    }
}
