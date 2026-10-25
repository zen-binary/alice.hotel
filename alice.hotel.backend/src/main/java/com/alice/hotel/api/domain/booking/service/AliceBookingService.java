package com.alice.hotel.api.domain.booking.service;

import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.common.Constants;
import com.alice.hotel.api.config.security.handler.CustomException;
import com.alice.hotel.api.domain.booking.BookingStatuses;
import com.alice.hotel.api.domain.booking.HouseStatuses;
import com.alice.hotel.api.dto.booking.BookingDtos.*;
import com.alice.hotel.api.entity.admin.hotel.HotelEntity;
import com.alice.hotel.api.entity.admin.hotelService.HotelServiceEntity;
import com.alice.hotel.api.entity.admin.rate.RateEntity;
import com.alice.hotel.api.entity.admin.room.RoomEntity;
import com.alice.hotel.api.entity.admin.roomType.RoomTypeEntity;
import com.alice.hotel.api.entity.admin.voucher.VoucherEntity;
import com.alice.hotel.api.entity.booking.*;
import com.alice.hotel.api.repository.admin.hotel.HotelRepository;
import com.alice.hotel.api.repository.admin.hotelService.HotelServiceRepository;
import com.alice.hotel.api.repository.admin.room.RoomRepository;
import com.alice.hotel.api.repository.admin.roomType.RoomTypeRepository;
import com.alice.hotel.api.repository.admin.voucher.VoucherRepository;
import com.alice.hotel.api.repository.booking.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AliceBookingService {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final HotelServiceRepository hotelServiceRepository;
    private final com.alice.hotel.api.repository.admin.rate.RateRepository rateRepository;
    private final VoucherRepository voucherRepository;
    private final BookingRepository bookingRepository;
    private final BookingServiceItemRepository bookingServiceItemRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final ShiftRepository shiftRepository;
    private final GuestProfileRepository guestProfileRepository;
    private final OtpRepository otpRepository;
    private final AuditLogRepository auditLogRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final ObjectProvider<JavaMailSender> mailSender;

    @Value("${alice.vnpay.tmn-code:ALICEDEMO}")
    private String vnpTmnCode;
    @Value("${alice.vnpay.hash-secret:ALICESECRET}")
    private String vnpHashSecret;
    @Value("${alice.vnpay.pay-url:https://sandbox.vnpayment.vn/paymentv2/vpcpay.html}")
    private String vnpPayUrl;
    @Value("${alice.vnpay.return-url:http://localhost:3000/payment/return}")
    private String vnpReturnUrl;
    @Value("${alice.mail.from:noreply@alice.hotel}")
    private String mailFrom;

    private String id(String prefix) {
        return prefix + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    private HotelEntity hotel() {
        return hotelRepository.findFirstByDelYn(Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
    }

    private void audit(String actor, String action, String type, String entityId, String detail) {
        AuditLogEntity logEntity = new AuditLogEntity();
        logEntity.setActorId(actor);
        logEntity.setAction(action);
        logEntity.setEntityType(type);
        logEntity.setEntityId(entityId);
        logEntity.setDetail(detail);
        logEntity.setRegDate(LocalDateTime.now());
        auditLogRepository.save(logEntity);
    }

    private void sendMailSafe(String to, String subject, String text) {
        JavaMailSender sender = mailSender.getIfAvailable();
        if (StringUtils.isBlank(to) || sender == null) {
            log.info("MAIL skip/to={} subject={} body={}", to, subject, text);
            return;
        }
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(mailFrom);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            sender.send(msg);
        } catch (Exception e) {
            log.warn("Mail failed: {}", e.getMessage());
        }
    }

    // ---------- Master ----------
    @Transactional(readOnly = true)
    public HotelDTO getHotel() {
        return modelMapper.map(hotel(), HotelDTO.class);
    }

    @Transactional
    public HotelDTO saveHotel(HotelDTO dto) {
        HotelEntity entity = hotelRepository.findById(dto.getHotelId()).orElseGet(HotelEntity::new);
        if (entity.getHotelId() == null) {
            entity.setHotelId(StringUtils.defaultIfBlank(dto.getHotelId(), id("HT")));
            entity.setDelYn(Constants.STATE_N);
            entity.setRegDate(LocalDateTime.now());
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setDescription(dto.getDescription());
        entity.setCheckInTime(dto.getCheckInTime());
        entity.setCheckOutTime(dto.getCheckOutTime());
        entity.setCancelFreeHours(dto.getCancelFreeHours());
        entity.setCancelRefundPercent(dto.getCancelRefundPercent());
        entity.setDepositPercent(dto.getDepositPercent());
        entity.setEarlyCheckinFee(dto.getEarlyCheckinFee());
        entity.setLateCheckoutFee(dto.getLateCheckoutFee());
        entity.setHoldMinutes(dto.getHoldMinutes());
        entity.setImageUrl(dto.getImageUrl());
        entity.setModDate(LocalDateTime.now());
        return modelMapper.map(hotelRepository.save(entity), HotelDTO.class);
    }

    @Transactional(readOnly = true)
    public List<RoomTypeDTO> listRoomTypes() {
        return roomTypeRepository.findByDelYnOrderByNameAsc(Constants.STATE_N).stream()
                .map(e -> modelMapper.map(e, RoomTypeDTO.class))
                .peek(dto -> {
                    List<ReviewEntity> reviews = reviewRepository.findByRoomTypeIdAndDelYnOrderByRegDateDesc(dto.getRoomTypeId(), Constants.STATE_N);
                    if (!reviews.isEmpty()) {
                        dto.setAvgRating(reviews.stream().mapToInt(ReviewEntity::getRating).average().orElse(0));
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public RoomTypeDTO saveRoomType(RoomTypeDTO dto) {
        RoomTypeEntity entity;
        if (StringUtils.isBlank(dto.getRoomTypeId())) {
            if (roomTypeRepository.existsByCodeAndDelYn(dto.getCode(), Constants.STATE_N)) {
                throw new CustomException(ApiStatus.CONFLICT);
            }
            entity = new RoomTypeEntity();
            entity.setRoomTypeId(id("RT"));
            entity.setDelYn(Constants.STATE_N);
            entity.setRegDate(LocalDateTime.now());
        } else {
            entity = roomTypeRepository.findByRoomTypeIdAndDelYn(dto.getRoomTypeId(), Constants.STATE_N)
                    .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setBasePrice(dto.getBasePrice());
        entity.setCapacityAdult(dto.getCapacityAdult());
        entity.setCapacityChild(dto.getCapacityChild());
        entity.setBedInfo(dto.getBedInfo());
        entity.setImageUrls(dto.getImageUrls());
        entity.setModDate(LocalDateTime.now());
        return modelMapper.map(roomTypeRepository.save(entity), RoomTypeDTO.class);
    }

    @Transactional
    public void deleteRoomType(String id) {
        RoomTypeEntity entity = roomTypeRepository.findByRoomTypeIdAndDelYn(id, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        entity.setDelYn(Constants.STATE_Y);
        roomTypeRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<RoomDTO> listRooms() {
        Map<String, String> typeNames = roomTypeRepository.findByDelYnOrderByNameAsc(Constants.STATE_N).stream()
                .collect(Collectors.toMap(RoomTypeEntity::getRoomTypeId, RoomTypeEntity::getName, (a, b) -> a));
        return roomRepository.findByDelYnOrderByRoomNumberAsc(Constants.STATE_N).stream().map(e -> {
            RoomDTO dto = modelMapper.map(e, RoomDTO.class);
            dto.setRoomTypeName(typeNames.get(e.getRoomTypeId()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public RoomDTO saveRoom(RoomDTO dto) {
        RoomEntity entity;
        if (StringUtils.isBlank(dto.getRoomId())) {
            entity = new RoomEntity();
            entity.setRoomId(id("RM"));
            entity.setDelYn(Constants.STATE_N);
            entity.setRegDate(LocalDateTime.now());
            entity.setHouseStatus(StringUtils.defaultIfBlank(dto.getHouseStatus(), HouseStatuses.VACANT_CLEAN));
            entity.setActiveYn(StringUtils.defaultIfBlank(dto.getActiveYn(), Constants.STATE_Y));
        } else {
            entity = roomRepository.findByRoomIdAndDelYn(dto.getRoomId(), Constants.STATE_N)
                    .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        }
        entity.setRoomTypeId(dto.getRoomTypeId());
        entity.setRoomNumber(dto.getRoomNumber());
        entity.setFloorNo(dto.getFloorNo());
        if (StringUtils.isNotBlank(dto.getHouseStatus())) {
            entity.setHouseStatus(dto.getHouseStatus());
        }
        if (StringUtils.isNotBlank(dto.getActiveYn())) {
            entity.setActiveYn(dto.getActiveYn());
        }
        entity.setNote(dto.getNote());
        entity.setModDate(LocalDateTime.now());
        return modelMapper.map(roomRepository.save(entity), RoomDTO.class);
    }

    @Transactional
    public RoomDTO updateHouseStatus(String roomId, HouseStatusRequest req, String actor) {
        RoomEntity entity = roomRepository.findByRoomIdAndDelYn(roomId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        entity.setHouseStatus(req.getHouseStatus());
        entity.setModDate(LocalDateTime.now());
        audit(actor, "HOUSE_STATUS", "ROOM", roomId, req.getHouseStatus());
        return modelMapper.map(roomRepository.save(entity), RoomDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ServiceDTO> listServices(boolean activeOnly) {
        List<HotelServiceEntity> list = activeOnly
                ? hotelServiceRepository.findByDelYnAndActiveYnOrderByNameAsc(Constants.STATE_N, Constants.STATE_Y)
                : hotelServiceRepository.findByDelYnOrderByNameAsc(Constants.STATE_N);
        return list.stream().map(e -> modelMapper.map(e, ServiceDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public ServiceDTO saveService(ServiceDTO dto) {
        HotelServiceEntity entity;
        if (StringUtils.isBlank(dto.getServiceId())) {
            entity = new HotelServiceEntity();
            entity.setServiceId(id("SV"));
            entity.setDelYn(Constants.STATE_N);
            entity.setActiveYn(StringUtils.defaultIfBlank(dto.getActiveYn(), Constants.STATE_Y));
        } else {
            entity = hotelServiceRepository.findByServiceIdAndDelYn(dto.getServiceId(), Constants.STATE_N)
                    .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setUnitName(dto.getUnitName());
        if (StringUtils.isNotBlank(dto.getActiveYn())) {
            entity.setActiveYn(dto.getActiveYn());
        }
        return modelMapper.map(hotelServiceRepository.save(entity), ServiceDTO.class);
    }

    @Transactional(readOnly = true)
    public List<VoucherDTO> listVouchers() {
        return voucherRepository.findByDelYnOrderByCodeAsc(Constants.STATE_N).stream()
                .map(e -> modelMapper.map(e, VoucherDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public VoucherDTO saveVoucher(VoucherDTO dto) {
        VoucherEntity entity;
        if (StringUtils.isBlank(dto.getVoucherId())) {
            entity = new VoucherEntity();
            entity.setVoucherId(id("VC"));
            entity.setDelYn(Constants.STATE_N);
            entity.setUsedCount(0);
            entity.setActiveYn(StringUtils.defaultIfBlank(dto.getActiveYn(), Constants.STATE_Y));
        } else {
            entity = voucherRepository.findByVoucherIdAndDelYn(dto.getVoucherId(), Constants.STATE_N)
                    .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDiscountType(dto.getDiscountType());
        entity.setDiscountValue(dto.getDiscountValue());
        entity.setMaxDiscount(dto.getMaxDiscount());
        entity.setMinSpend(dto.getMinSpend());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setUsageLimit(dto.getUsageLimit());
        if (StringUtils.isNotBlank(dto.getActiveYn())) {
            entity.setActiveYn(dto.getActiveYn());
        }
        return modelMapper.map(voucherRepository.save(entity), VoucherDTO.class);
    }

    // ---------- Availability & booking ----------
    private long nights(LocalDate in, LocalDate out) {
        long n = ChronoUnit.DAYS.between(in, out);
        if (n <= 0) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
        return n;
    }

    private BigDecimal priceForNight(String roomTypeId, LocalDate day, BigDecimal fallback) {
        List<RateEntity> rates = rateRepository.findByRoomTypeIdAndDelYn(roomTypeId, Constants.STATE_N);
        for (RateEntity rate : rates) {
            if (rate.getStartDate() != null && day.isBefore(rate.getStartDate())) continue;
            if (rate.getEndDate() != null && day.isAfter(rate.getEndDate())) continue;
            if (Constants.STATE_Y.equals(rate.getStopSellYn())) {
                throw new CustomException(ApiStatus.CONFLICT);
            }
            if (StringUtils.isNotBlank(rate.getDowMask())) {
                int dow = day.getDayOfWeek().getValue() % 7; // Mon=1..Sun=7 -> Sun=0 style optional
                char marker = Character.forDigit(day.getDayOfWeek().getValue() % 7, 10);
                if (rate.getDowMask().indexOf(marker) < 0 && rate.getDowMask().indexOf(String.valueOf(day.getDayOfWeek().getValue())) < 0) {
                    continue;
                }
            }
            return rate.getPrice() != null ? rate.getPrice() : fallback;
        }
        return fallback;
    }

    private BigDecimal calcRoomAmount(RoomTypeEntity type, LocalDate in, LocalDate out) {
        BigDecimal total = BigDecimal.ZERO;
        for (LocalDate d = in; d.isBefore(out); d = d.plusDays(1)) {
            total = total.add(priceForNight(type.getRoomTypeId(), d, type.getBasePrice()));
        }
        return total;
    }

    @Transactional(readOnly = true)
    public List<RateDTO> listRates(String roomTypeId) {
        List<RateEntity> list = StringUtils.isBlank(roomTypeId)
                ? rateRepository.findAll().stream().filter(r -> !Constants.STATE_Y.equals(r.getDelYn())).collect(Collectors.toList())
                : rateRepository.findByRoomTypeIdAndDelYn(roomTypeId, Constants.STATE_N);
        Map<String, String> names = roomTypeRepository.findByDelYnOrderByNameAsc(Constants.STATE_N).stream()
                .collect(Collectors.toMap(RoomTypeEntity::getRoomTypeId, RoomTypeEntity::getName, (a, b) -> a));
        return list.stream().map(e -> {
            RateDTO dto = modelMapper.map(e, RateDTO.class);
            dto.setRoomTypeName(names.get(e.getRoomTypeId()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public RateDTO saveRate(RateDTO dto) {
        RateEntity entity;
        if (StringUtils.isBlank(dto.getRateId())) {
            entity = new RateEntity();
            entity.setRateId(id("RT"));
            entity.setDelYn(Constants.STATE_N);
        } else {
            entity = rateRepository.findById(dto.getRateId()).orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        }
        entity.setRoomTypeId(dto.getRoomTypeId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setPrice(dto.getPrice());
        entity.setDowMask(dto.getDowMask());
        entity.setStopSellYn(StringUtils.defaultIfBlank(dto.getStopSellYn(), Constants.STATE_N));
        entity.setName(dto.getName());
        return modelMapper.map(rateRepository.save(entity), RateDTO.class);
    }

    @Transactional
    public void deleteRate(String rateId) {
        RateEntity entity = rateRepository.findById(rateId).orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        entity.setDelYn(Constants.STATE_Y);
        rateRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<TimelineBooking> timeline(LocalDate from, LocalDate to) {
        LocalDate f = from == null ? LocalDate.now() : from;
        LocalDate t = to == null ? f.plusDays(14) : to;
        return bookingRepository.findByDelYnOrderByRegDateDesc(Constants.STATE_N).stream()
                .filter(b -> !List.of(BookingStatuses.CANCELLED, BookingStatuses.EXPIRED_HOLD).contains(b.getStatus()))
                .filter(b -> b.getCheckIn() != null && b.getCheckOut() != null)
                .filter(b -> b.getCheckIn().isBefore(t) && b.getCheckOut().isAfter(f))
                .map(b -> {
                    TimelineBooking tb = new TimelineBooking();
                    tb.setBookingId(b.getBookingId());
                    tb.setBookingCode(b.getBookingCode());
                    tb.setRoomId(b.getRoomId());
                    tb.setGuestName(b.getGuestName());
                    tb.setStatus(b.getStatus());
                    tb.setCheckIn(b.getCheckIn());
                    tb.setCheckOut(b.getCheckOut());
                    if (b.getRoomId() != null) {
                        roomRepository.findById(b.getRoomId()).ifPresent(r -> tb.setRoomNumber(r.getRoomNumber()));
                    }
                    return tb;
                }).collect(Collectors.toList());
    }

    private BigDecimal applyVoucher(VoucherEntity voucher, BigDecimal base) {
        if (voucher == null) {
            return BigDecimal.ZERO;
        }
        LocalDate today = LocalDate.now();
        if (!Constants.STATE_Y.equals(voucher.getActiveYn())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (voucher.getStartDate() != null && today.isBefore(voucher.getStartDate())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (voucher.getEndDate() != null && today.isAfter(voucher.getEndDate())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (voucher.getUsageLimit() != null && voucher.getUsedCount() != null
                && voucher.getUsedCount() >= voucher.getUsageLimit()) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (voucher.getMinSpend() != null && base.compareTo(voucher.getMinSpend()) < 0) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        BigDecimal discount;
        if ("PERCENT".equalsIgnoreCase(voucher.getDiscountType())) {
            discount = base.multiply(voucher.getDiscountValue()).divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
            if (voucher.getMaxDiscount() != null) {
                discount = discount.min(voucher.getMaxDiscount());
            }
        } else {
            discount = voucher.getDiscountValue();
        }
        return discount.min(base);
    }

    @Transactional(readOnly = true)
    public List<RoomTypeDTO> searchAvailability(AvailabilityRequest req) {
        if (req.getCheckIn() == null || req.getCheckOut() == null) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
        int adults = req.getAdults() == null ? 1 : req.getAdults();
        int children = req.getChildren() == null ? 0 : req.getChildren();
        List<RoomTypeEntity> types = roomTypeRepository.findByDelYnOrderByNameAsc(Constants.STATE_N);
        List<RoomTypeDTO> result = new ArrayList<>();
        for (RoomTypeEntity type : types) {
            if (StringUtils.isNotBlank(req.getRoomTypeId()) && !type.getRoomTypeId().equals(req.getRoomTypeId())) {
                continue;
            }
            if (type.getCapacityAdult() < adults || type.getCapacityChild() < children) {
                continue;
            }
            List<RoomEntity> available = roomRepository.findAvailable(type.getRoomTypeId(), req.getCheckIn(), req.getCheckOut());
            if (available.isEmpty()) {
                continue;
            }
            RoomTypeDTO dto = modelMapper.map(type, RoomTypeDTO.class);
            dto.setAvailableRooms((long) available.size());
            dto.setEstimatedTotal(calcRoomAmount(type, req.getCheckIn(), req.getCheckOut()));
            List<ReviewEntity> reviews = reviewRepository.findByRoomTypeIdAndDelYnOrderByRegDateDesc(type.getRoomTypeId(), Constants.STATE_N);
            if (!reviews.isEmpty()) {
                dto.setAvgRating(reviews.stream().mapToInt(ReviewEntity::getRating).average().orElse(0));
            }
            result.add(dto);
        }
        return result;
    }

    private BookingDTO toBookingDto(BookingEntity b) {
        BookingDTO dto = modelMapper.map(b, BookingDTO.class);
        roomTypeRepository.findById(b.getRoomTypeId()).ifPresent(rt -> dto.setRoomTypeName(rt.getName()));
        if (b.getRoomId() != null) {
            roomRepository.findById(b.getRoomId()).ifPresent(r -> dto.setRoomNumber(r.getRoomNumber()));
        }
        BigDecimal paid = b.getPaidAmount() == null ? BigDecimal.ZERO : b.getPaidAmount();
        BigDecimal total = b.getTotalAmount() == null ? BigDecimal.ZERO : b.getTotalAmount();
        dto.setRemainingAmount(total.subtract(paid).max(BigDecimal.ZERO));
        dto.setRefundEstimate(estimateRefund(b));
        List<BookingServiceEntity> lines = bookingServiceItemRepository.findByBookingId(b.getBookingId());
        Map<String, String> serviceNames = hotelServiceRepository.findByDelYnOrderByNameAsc(Constants.STATE_N).stream()
                .collect(Collectors.toMap(HotelServiceEntity::getServiceId, HotelServiceEntity::getName, (a, c) -> a));
        for (BookingServiceEntity line : lines) {
            ServiceLineDTO s = modelMapper.map(line, ServiceLineDTO.class);
            s.setServiceName(serviceNames.get(line.getServiceId()));
            dto.getServices().add(s);
        }
        paymentRepository.findByBookingIdOrderByRegDateAsc(b.getBookingId()).forEach(p ->
                dto.getPayments().add(modelMapper.map(p, PaymentDTO.class)));
        return dto;
    }

    private BigDecimal estimateRefund(BookingEntity b) {
        if (!List.of(BookingStatuses.CONFIRMED, BookingStatuses.PENDING_PAYMENT).contains(b.getStatus())) {
            return BigDecimal.ZERO;
        }
        HotelEntity h = hotel();
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), b.getCheckIn().atStartOfDay());
        BigDecimal paid = b.getPaidAmount() == null ? BigDecimal.ZERO : b.getPaidAmount();
        if (hours >= h.getCancelFreeHours()) {
            return paid.multiply(BigDecimal.valueOf(h.getCancelRefundPercent()))
                    .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    public BookingDTO createBooking(CreateBookingRequest req, String userId) {
        if (req.getCheckIn() == null || req.getCheckOut() == null || StringUtils.isBlank(req.getRoomTypeId())
                || StringUtils.isBlank(req.getGuestName())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
        if (req.getCheckIn().isBefore(LocalDate.now()) || !req.getCheckOut().isAfter(req.getCheckIn())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
        if (StringUtils.isNotBlank(userId)) {
            guestProfileRepository.findById(userId).ifPresent(g -> {
                if (Constants.STATE_Y.equals(g.getLockedYn())) {
                    throw new CustomException(ApiStatus.FORBIDDEN);
                }
            });
        }
        RoomTypeEntity type = roomTypeRepository.findByRoomTypeIdAndDelYn(req.getRoomTypeId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        List<RoomEntity> available = roomRepository.findAvailable(type.getRoomTypeId(), req.getCheckIn(), req.getCheckOut());
        if (available.isEmpty()) {
            throw new CustomException(ApiStatus.CONFLICT);
        }
        RoomEntity room = null;
        if (StringUtils.isNotBlank(req.getRoomId())) {
            room = available.stream().filter(r -> r.getRoomId().equals(req.getRoomId())).findFirst()
                    .orElseThrow(() -> new CustomException(ApiStatus.CONFLICT));
        } else {
            room = available.get(0);
        }

        HotelEntity hotel = hotel();
        BigDecimal roomAmount = calcRoomAmount(type, req.getCheckIn(), req.getCheckOut());
        BigDecimal serviceAmount = BigDecimal.ZERO;
        List<BookingServiceEntity> lines = new ArrayList<>();
        if (req.getServices() != null) {
            for (ServiceLineRequest lineReq : req.getServices()) {
                HotelServiceEntity svc = hotelServiceRepository.findByServiceIdAndDelYn(lineReq.getServiceId(), Constants.STATE_N)
                        .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
                int qty = lineReq.getQty() == null || lineReq.getQty() < 1 ? 1 : lineReq.getQty();
                BigDecimal amount = svc.getUnitPrice().multiply(BigDecimal.valueOf(qty));
                serviceAmount = serviceAmount.add(amount);
                BookingServiceEntity line = new BookingServiceEntity();
                line.setServiceId(svc.getServiceId());
                line.setQty(qty);
                line.setUnitPrice(svc.getUnitPrice());
                line.setAmount(amount);
                line.setNote(lineReq.getNote());
                lines.add(line);
            }
        }

        VoucherEntity voucher = null;
        BigDecimal discount = BigDecimal.ZERO;
        if (StringUtils.isNotBlank(req.getVoucherCode())) {
            voucher = voucherRepository.findByCodeAndDelYn(req.getVoucherCode().trim(), Constants.STATE_N)
                    .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
            discount = applyVoucher(voucher, roomAmount.add(serviceAmount));
        }

        BigDecimal total = roomAmount.add(serviceAmount).subtract(discount).max(BigDecimal.ZERO);
        boolean payAtHotel = Boolean.TRUE.equals(req.getPayAtHotel());
        BookingEntity booking = new BookingEntity();
        booking.setBookingId(id("BK"));
        booking.setBookingCode("A" + System.currentTimeMillis() % 1000000000L);
        booking.setUserId(userId);
        booking.setGuestName(req.getGuestName());
        booking.setGuestPhone(req.getGuestPhone());
        booking.setGuestEmail(req.getGuestEmail());
        booking.setGuestIdNumber(req.getGuestIdNumber());
        booking.setRoomTypeId(type.getRoomTypeId());
        booking.setRoomId(room.getRoomId());
        booking.setCheckIn(req.getCheckIn());
        booking.setCheckOut(req.getCheckOut());
        booking.setAdults(req.getAdults() == null ? 1 : req.getAdults());
        booking.setChildren(req.getChildren() == null ? 0 : req.getChildren());
        booking.setSource(StringUtils.defaultIfBlank(req.getSource(), "ONLINE"));
        booking.setRoomAmount(roomAmount);
        booking.setServiceAmount(serviceAmount);
        booking.setDiscountAmount(discount);
        booking.setFeeAmount(BigDecimal.ZERO);
        booking.setTotalAmount(total);
        booking.setPaidAmount(BigDecimal.ZERO);
        booking.setVoucherId(voucher == null ? null : voucher.getVoucherId());
        booking.setNote(req.getNote());
        booking.setReminderSentYn(Constants.STATE_N);
        booking.setDelYn(Constants.STATE_N);
        booking.setRegId(userId);
        booking.setRegDate(LocalDateTime.now());
        if (payAtHotel) {
            booking.setStatus(BookingStatuses.CONFIRMED);
            booking.setHoldExpireAt(null);
        } else {
            booking.setStatus(BookingStatuses.PENDING_PAYMENT);
            int hold = hotel.getHoldMinutes() == null ? 15 : hotel.getHoldMinutes();
            booking.setHoldExpireAt(LocalDateTime.now().plusMinutes(hold));
        }
        bookingRepository.save(booking);
        for (BookingServiceEntity line : lines) {
            line.setBookingId(booking.getBookingId());
            bookingServiceItemRepository.save(line);
        }
        if (voucher != null) {
            voucher.setUsedCount((voucher.getUsedCount() == null ? 0 : voucher.getUsedCount()) + 1);
            voucherRepository.save(voucher);
        }
        sendMailSafe(booking.getGuestEmail(), "Alice Hotel — Xac nhan dat phong " + booking.getBookingCode(),
                "Ma don: " + booking.getBookingCode() + "\nCheck-in: " + booking.getCheckIn()
                        + "\nCheck-out: " + booking.getCheckOut() + "\nTong: " + booking.getTotalAmount()
                        + "\nTrang thai: " + booking.getStatus());
        audit(userId, "CREATE_BOOKING", "BOOKING", booking.getBookingId(), booking.getBookingCode());
        return toBookingDto(booking);
    }

    @Transactional(readOnly = true)
    public BookingDTO getBooking(String bookingId) {
        return toBookingDto(bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> myBookings(String userId) {
        return bookingRepository.findByUserIdAndDelYnOrderByRegDateDesc(userId, Constants.STATE_N).stream()
                .map(this::toBookingDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> adminBookings(String status, LocalDate from, LocalDate to) {
        return bookingRepository.searchAdmin(status, from, to).stream().map(this::toBookingDto).collect(Collectors.toList());
    }

    @Transactional
    public BookingDTO cancelBooking(String bookingId, CancelRequest req, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (List.of(BookingStatuses.CHECKED_IN, BookingStatuses.CHECKED_OUT, BookingStatuses.CANCELLED).contains(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        BigDecimal refund = estimateRefund(b);
        boolean unpaid = nz(b.getPaidAmount()).compareTo(BigDecimal.ZERO) == 0;
        b.setStatus(BookingStatuses.CANCELLED);
        b.setCancelReason(req == null ? null : req.getReason());
        b.setModDate(LocalDateTime.now());
        b.setModId(actor);
        if (unpaid && StringUtils.isNotBlank(b.getVoucherId())) {
            voucherRepository.findByVoucherIdAndDelYn(b.getVoucherId(), Constants.STATE_N).ifPresent(v -> {
                int used = v.getUsedCount() == null ? 0 : v.getUsedCount();
                v.setUsedCount(Math.max(0, used - 1));
                voucherRepository.save(v);
            });
        }
        if (refund.compareTo(BigDecimal.ZERO) > 0 && nz(b.getPaidAmount()).compareTo(BigDecimal.ZERO) > 0) {
            PaymentEntity refundPay = new PaymentEntity();
            refundPay.setPaymentId(id("PAY"));
            refundPay.setBookingId(b.getBookingId());
            refundPay.setMethod("REFUND");
            refundPay.setAmount(refund.negate());
            refundPay.setStatus("SUCCESS");
            refundPay.setRegDate(LocalDateTime.now());
            refundPay.setRegId(actor);
            paymentRepository.save(refundPay);
            b.setPaidAmount(b.getPaidAmount().subtract(refund).max(BigDecimal.ZERO));
        }
        bookingRepository.save(b);
        sendMailSafe(b.getGuestEmail(), "Alice Hotel — Huy dat phong " + b.getBookingCode(),
                "Don " + b.getBookingCode() + " da huy. Hoan uoc tinh: " + refund);
        audit(actor, "CANCEL", "BOOKING", bookingId, b.getCancelReason());
        return toBookingDto(b);
    }

    @Transactional
    public BookingDTO modifyBooking(String bookingId, ModifyBookingRequest req, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (!List.of(BookingStatuses.CONFIRMED, BookingStatuses.PENDING_PAYMENT).contains(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        LocalDate in = req.getCheckIn() == null ? b.getCheckIn() : req.getCheckIn();
        LocalDate out = req.getCheckOut() == null ? b.getCheckOut() : req.getCheckOut();
        String typeId = StringUtils.defaultIfBlank(req.getRoomTypeId(), b.getRoomTypeId());
        List<RoomEntity> available = roomRepository.findAvailable(typeId, in, out).stream()
                .filter(r -> b.getRoomId() == null || !r.getRoomId().equals(b.getRoomId()) || true)
                .collect(Collectors.toList());
        // allow current room if still free ignoring self
        available = roomRepository.findByRoomTypeIdAndDelYnAndActiveYn(typeId, Constants.STATE_N, Constants.STATE_Y).stream()
                .filter(r -> !HouseStatuses.OUT_OF_ORDER.equals(r.getHouseStatus()))
                .filter(r -> {
                    long overlaps = bookingRepository.findByDelYnOrderByRegDateDesc(Constants.STATE_N).stream()
                            .filter(x -> !x.getBookingId().equals(b.getBookingId()))
                            .filter(x -> r.getRoomId().equals(x.getRoomId()))
                            .filter(x -> List.of(BookingStatuses.DRAFT_HOLD, BookingStatuses.PENDING_PAYMENT,
                                    BookingStatuses.CONFIRMED, BookingStatuses.CHECKED_IN).contains(x.getStatus()))
                            .filter(x -> x.getCheckIn().isBefore(out) && x.getCheckOut().isAfter(in))
                            .count();
                    return overlaps == 0;
                }).collect(Collectors.toList());
        if (available.isEmpty()) {
            throw new CustomException(ApiStatus.CONFLICT);
        }
        RoomTypeEntity type = roomTypeRepository.findByRoomTypeIdAndDelYn(typeId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        b.setRoomTypeId(typeId);
        b.setRoomId(available.get(0).getRoomId());
        b.setCheckIn(in);
        b.setCheckOut(out);
        b.setRoomAmount(calcRoomAmount(type, in, out));
        BigDecimal total = b.getRoomAmount().add(nz(b.getServiceAmount())).subtract(nz(b.getDiscountAmount())).add(nz(b.getFeeAmount()));
        b.setTotalAmount(total.max(BigDecimal.ZERO));
        b.setModId(actor);
        b.setModDate(LocalDateTime.now());
        bookingRepository.save(b);
        sendMailSafe(b.getGuestEmail(), "Alice Hotel — Doi lich " + b.getBookingCode(),
                "Don " + b.getBookingCode() + " doi sang " + in + " -> " + out);
        audit(actor, "MODIFY", "BOOKING", bookingId, in + "/" + out);
        return toBookingDto(b);
    }

    private BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    // ---------- Payments / VNPay ----------
    @Transactional
    public VnPayCreateResponse createVnPayUrl(String bookingId, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (!List.of(BookingStatuses.PENDING_PAYMENT, BookingStatuses.CONFIRMED).contains(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        HotelEntity hotel = hotel();
        BigDecimal remaining = nz(b.getTotalAmount()).subtract(nz(b.getPaidAmount()));
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        BigDecimal deposit = remaining.multiply(BigDecimal.valueOf(hotel.getDepositPercent()))
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        if (deposit.compareTo(BigDecimal.ZERO) <= 0) {
            deposit = remaining;
        }
        String txnRef = b.getBookingCode() + System.currentTimeMillis() % 100000;
        PaymentEntity pay = new PaymentEntity();
        pay.setPaymentId(id("PAY"));
        pay.setBookingId(b.getBookingId());
        pay.setMethod("VNPAY");
        pay.setAmount(deposit);
        pay.setStatus("PENDING");
        pay.setTxnRef(txnRef);
        pay.setRegId(actor);
        pay.setRegDate(LocalDateTime.now());
        paymentRepository.save(pay);

        long amountVnd = deposit.longValue() * 100;
        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", vnpTmnCode);
        params.put("vnp_Amount", String.valueOf(amountVnd));
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", txnRef);
        params.put("vnp_OrderInfo", "Alice booking " + b.getBookingCode());
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", vnpReturnUrl);
        params.put("vnp_IpAddr", "127.0.0.1");
        params.put("vnp_CreateDate", java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
        String hashData = params.entrySet().stream()
                .map(e -> e.getKey() + "=" + urlEncode(e.getValue()))
                .collect(Collectors.joining("&"));
        String secureHash = hmacSHA512(vnpHashSecret, hashData);
        String paymentUrl = vnpPayUrl + "?" + hashData + "&vnp_SecureHash=" + secureHash;
        VnPayCreateResponse res = new VnPayCreateResponse();
        res.setPaymentUrl(paymentUrl);
        res.setTxnRef(txnRef);
        res.setAmount(deposit);
        return res;
    }

    @Transactional
    public BookingDTO confirmVnPay(Map<String, String> params) {
        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.getOrDefault("vnp_ResponseCode", "");
        if (StringUtils.isBlank(txnRef)) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }
        List<PaymentEntity> matches = paymentRepository.findByTxnRefOrderByRegDateDesc(txnRef);
        if (matches == null || matches.isEmpty()) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }
        PaymentEntity pay = matches.stream()
                .filter(p -> "SUCCESS".equals(p.getStatus()))
                .findFirst()
                .orElse(matches.get(0));
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(pay.getBookingId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        // Idempotent: Return URL + IPN may both call this
        if ("SUCCESS".equals(pay.getStatus())) {
            return toBookingDto(b);
        }
        if ("00".equals(responseCode)) {
            pay.setStatus("SUCCESS");
            pay.setVnpTxnNo(params.get("vnp_TransactionNo"));
            b.setPaidAmount(nz(b.getPaidAmount()).add(nz(pay.getAmount())));
            b.setStatus(BookingStatuses.CONFIRMED);
            b.setHoldExpireAt(null);
            sendMailSafe(b.getGuestEmail(), "Alice Hotel — Thanh toan thanh cong " + b.getBookingCode(),
                    "Da nhan coc " + pay.getAmount() + " VND qua VNPay.");
        } else {
            pay.setStatus("FAILED");
        }
        paymentRepository.save(pay);
        bookingRepository.save(b);
        return toBookingDto(b);
    }

    @Transactional
    public BookingDTO counterPayment(PaymentRequest req, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(req.getBookingId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        ShiftEntity openShift = shiftRepository.findFirstByUserIdAndStatus(actor, "OPEN")
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_BUSINESS));
        BigDecimal amount = req.getAmount() == null ? nz(b.getTotalAmount()).subtract(nz(b.getPaidAmount())) : req.getAmount();
        PaymentEntity pay = new PaymentEntity();
        pay.setPaymentId(id("PAY"));
        pay.setBookingId(b.getBookingId());
        pay.setMethod(StringUtils.defaultIfBlank(req.getMethod(), "CASH"));
        pay.setAmount(amount);
        pay.setStatus("SUCCESS");
        pay.setNote(req.getNote());
        pay.setRegId(actor);
        pay.setRegDate(LocalDateTime.now());
        pay.setShiftId(openShift.getShiftId());
        paymentRepository.save(pay);
        b.setPaidAmount(nz(b.getPaidAmount()).add(amount));
        if (BookingStatuses.PENDING_PAYMENT.equals(b.getStatus()) && nz(b.getPaidAmount()).compareTo(BigDecimal.ZERO) > 0) {
            b.setStatus(BookingStatuses.CONFIRMED);
            b.setHoldExpireAt(null);
        }
        b.setModDate(LocalDateTime.now());
        bookingRepository.save(b);
        audit(actor, "COUNTER_PAY", "BOOKING", b.getBookingId(), pay.getMethod() + ":" + amount);
        return toBookingDto(b);
    }

    private String urlEncode(String v) {
        try {
            return java.net.URLEncoder.encode(v, StandardCharsets.UTF_8).replace("+", "%20");
        } catch (Exception e) {
            return v;
        }
    }

    private String hmacSHA512(String key, String data) {
        try {
            javax.crypto.Mac hmac = javax.crypto.Mac.getInstance("HmacSHA512");
            hmac.init(new javax.crypto.spec.SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new CustomException(ApiStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ---------- Front desk ----------
    @Transactional(readOnly = true)
    public FrontDeskBoard frontDeskBoard() {
        FrontDeskBoard board = new FrontDeskBoard();
        board.setRooms(listRooms());
        LocalDate today = LocalDate.now();
        board.setArrivals(bookingRepository.arrivals(today).stream().map(this::toBookingDto).collect(Collectors.toList()));
        board.setDepartures(bookingRepository.departures(today).stream().map(this::toBookingDto).collect(Collectors.toList()));
        board.setInHouse(bookingRepository.inHouse().stream().map(this::toBookingDto).collect(Collectors.toList()));
        board.setDirtyQueue(listRooms().stream()
                .filter(r -> HouseStatuses.VACANT_DIRTY.equals(r.getHouseStatus()))
                .map(r -> {
                    BookingDTO stub = new BookingDTO();
                    stub.setRoomId(r.getRoomId());
                    stub.setRoomNumber(r.getRoomNumber());
                    stub.setNote("DIRTY");
                    return stub;
                }).collect(Collectors.toList()));
        return board;
    }

    @Transactional
    public BookingDTO checkIn(String bookingId, CheckInRequest req, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (!BookingStatuses.CONFIRMED.equals(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        String roomId = StringUtils.defaultIfBlank(req.getRoomId(), b.getRoomId());
        RoomEntity room = roomRepository.findByRoomIdAndDelYn(roomId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (HouseStatuses.OUT_OF_ORDER.equals(room.getHouseStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (!HouseStatuses.VACANT_CLEAN.equals(room.getHouseStatus())) {
            if (!Boolean.TRUE.equals(req.getForceDirtyOverride())) {
                throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
            }
            audit(actor, "CHECK_IN_OVERRIDE", "ROOM", roomId,
                    StringUtils.defaultIfBlank(req.getOverrideReason(), "force dirty/ooo override"));
        }
        if (Boolean.TRUE.equals(req.getEarlyCheckIn())) {
            HotelEntity h = hotel();
            b.setFeeAmount(nz(b.getFeeAmount()).add(nz(h.getEarlyCheckinFee())));
            b.setTotalAmount(nz(b.getRoomAmount()).add(nz(b.getServiceAmount())).subtract(nz(b.getDiscountAmount())).add(nz(b.getFeeAmount())));
        }
        if (StringUtils.isNotBlank(req.getGuestIdNumber())) {
            b.setGuestIdNumber(req.getGuestIdNumber());
        }
        b.setRoomId(roomId);
        b.setStatus(BookingStatuses.CHECKED_IN);
        b.setModId(actor);
        b.setModDate(LocalDateTime.now());
        room.setHouseStatus(HouseStatuses.OCCUPIED);
        roomRepository.save(room);
        bookingRepository.save(b);
        audit(actor, "CHECK_IN", "BOOKING", bookingId, room.getRoomNumber());
        return toBookingDto(b);
    }

    @Transactional
    public BookingDTO checkOut(String bookingId, CheckOutRequest req, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (!BookingStatuses.CHECKED_IN.equals(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (Boolean.TRUE.equals(req.getLateCheckOut())) {
            HotelEntity h = hotel();
            b.setFeeAmount(nz(b.getFeeAmount()).add(nz(h.getLateCheckoutFee())));
            b.setTotalAmount(nz(b.getRoomAmount()).add(nz(b.getServiceAmount())).subtract(nz(b.getDiscountAmount())).add(nz(b.getFeeAmount())));
        }
        BigDecimal remaining = nz(b.getTotalAmount()).subtract(nz(b.getPaidAmount()));
        if (remaining.compareTo(BigDecimal.ZERO) > 0) {
            PaymentRequest payReq = new PaymentRequest();
            payReq.setBookingId(bookingId);
            payReq.setMethod(StringUtils.defaultIfBlank(req.getMethod(), "CASH"));
            payReq.setAmount(req.getAmount() == null ? remaining : req.getAmount());
            counterPayment(payReq, actor);
            b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N).orElseThrow();
        }
        b.setStatus(BookingStatuses.CHECKED_OUT);
        b.setModId(actor);
        b.setModDate(LocalDateTime.now());
        if (b.getRoomId() != null) {
            roomRepository.findByRoomIdAndDelYn(b.getRoomId(), Constants.STATE_N).ifPresent(room -> {
                room.setHouseStatus(HouseStatuses.VACANT_DIRTY);
                roomRepository.save(room);
            });
        }
        bookingRepository.save(b);
        audit(actor, "CHECK_OUT", "BOOKING", bookingId, null);
        return toBookingDto(b);
    }

    @Transactional
    public BookingDTO roomMove(String bookingId, RoomMoveRequest req, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (!BookingStatuses.CHECKED_IN.equals(b.getStatus()) && !BookingStatuses.CONFIRMED.equals(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        RoomEntity newRoom = roomRepository.findByRoomIdAndDelYn(req.getNewRoomId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (b.getRoomId() != null) {
            roomRepository.findByRoomIdAndDelYn(b.getRoomId(), Constants.STATE_N).ifPresent(old -> {
                old.setHouseStatus(HouseStatuses.VACANT_DIRTY);
                roomRepository.save(old);
            });
        }
        b.setRoomId(newRoom.getRoomId());
        b.setRoomTypeId(newRoom.getRoomTypeId());
        if (BookingStatuses.CHECKED_IN.equals(b.getStatus())) {
            newRoom.setHouseStatus(HouseStatuses.OCCUPIED);
            roomRepository.save(newRoom);
        }
        b.setModDate(LocalDateTime.now());
        b.setModId(actor);
        bookingRepository.save(b);
        audit(actor, "ROOM_MOVE", "BOOKING", bookingId, newRoom.getRoomNumber());
        return toBookingDto(b);
    }

    @Transactional
    public BookingDTO addService(String bookingId, ServiceLineRequest req, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        HotelServiceEntity svc = hotelServiceRepository.findByServiceIdAndDelYn(req.getServiceId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        int qty = req.getQty() == null || req.getQty() < 1 ? 1 : req.getQty();
        BigDecimal amount = svc.getUnitPrice().multiply(BigDecimal.valueOf(qty));
        BookingServiceEntity line = new BookingServiceEntity();
        line.setBookingId(bookingId);
        line.setServiceId(svc.getServiceId());
        line.setQty(qty);
        line.setUnitPrice(svc.getUnitPrice());
        line.setAmount(amount);
        line.setNote(req.getNote());
        bookingServiceItemRepository.save(line);
        b.setServiceAmount(nz(b.getServiceAmount()).add(amount));
        b.setTotalAmount(nz(b.getRoomAmount()).add(nz(b.getServiceAmount())).subtract(nz(b.getDiscountAmount())).add(nz(b.getFeeAmount())));
        b.setModDate(LocalDateTime.now());
        bookingRepository.save(b);
        audit(actor, "ADD_SERVICE", "BOOKING", bookingId, svc.getCode() + " x" + qty);
        return toBookingDto(b);
    }

    @Transactional
    public BookingDTO markNoShow(String bookingId, String actor) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(bookingId, Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (!BookingStatuses.CONFIRMED.equals(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (b.getCheckIn() != null && b.getCheckIn().isAfter(LocalDate.now())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        b.setStatus(BookingStatuses.NO_SHOW);
        b.setModId(actor);
        b.setModDate(LocalDateTime.now());
        bookingRepository.save(b);
        audit(actor, "NO_SHOW", "BOOKING", bookingId, null);
        return toBookingDto(b);
    }

    // ---------- Shift ----------
    @Transactional
    public ShiftDTO openShift(ShiftOpenRequest req, String userId) {
        shiftRepository.findFirstByUserIdAndStatus(userId, "OPEN").ifPresent(s -> {
            throw new CustomException(ApiStatus.CONFLICT);
        });
        ShiftEntity shift = new ShiftEntity();
        shift.setShiftId(id("SH"));
        shift.setUserId(userId);
        shift.setOpenedAt(LocalDateTime.now());
        shift.setOpeningCash(req.getOpeningCash() == null ? BigDecimal.ZERO : req.getOpeningCash());
        shift.setNote(req.getNote());
        shift.setStatus("OPEN");
        shift.setCashTotal(BigDecimal.ZERO);
        shift.setTransferTotal(BigDecimal.ZERO);
        shift.setOnlineTotal(BigDecimal.ZERO);
        return modelMapper.map(shiftRepository.save(shift), ShiftDTO.class);
    }

    @Transactional
    public ShiftDTO closeShift(ShiftCloseRequest req, String userId) {
        ShiftEntity shift = shiftRepository.findFirstByUserIdAndStatus(userId, "OPEN").orElse(null);
        if (shift == null) {
            return null;
        }
        List<PaymentEntity> pays = paymentRepository.findByShiftId(shift.getShiftId());
        BigDecimal cash = BigDecimal.ZERO, transfer = BigDecimal.ZERO, online = BigDecimal.ZERO;
        for (PaymentEntity p : pays) {
            if (!"SUCCESS".equals(p.getStatus())) continue;
            if ("CASH".equalsIgnoreCase(p.getMethod())) cash = cash.add(p.getAmount());
            else if ("TRANSFER".equalsIgnoreCase(p.getMethod())) transfer = transfer.add(p.getAmount());
            else online = online.add(p.getAmount());
        }
        shift.setCashTotal(cash);
        shift.setTransferTotal(transfer);
        shift.setOnlineTotal(online);
        shift.setExpectedCash(nz(shift.getOpeningCash()).add(cash));
        shift.setClosingCash(req.getClosingCash());
        shift.setClosedAt(LocalDateTime.now());
        shift.setNote(req.getNote());
        shift.setStatus("CLOSED");
        audit(userId, "CLOSE_SHIFT", "SHIFT", shift.getShiftId(), "cash=" + cash);
        return modelMapper.map(shiftRepository.save(shift), ShiftDTO.class);
    }

    @Transactional(readOnly = true)
    public ShiftDTO currentShift(String userId) {
        return shiftRepository.findFirstByUserIdAndStatus(userId, "OPEN")
                .map(s -> modelMapper.map(s, ShiftDTO.class))
                .orElse(null);
    }

    // ---------- Auth guest / OTP ----------
    @Transactional
    public void sendOtp(OtpRequest req) {
        if (StringUtils.isBlank(req.getEmail())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
        String code = String.format("%06d", new Random().nextInt(1000000));
        OtpEntity otp = new OtpEntity();
        otp.setEmail(req.getEmail());
        otp.setOtpCode(code);
        otp.setPurpose(StringUtils.defaultIfBlank(req.getPurpose(), "REGISTER"));
        otp.setExpireAt(LocalDateTime.now().plusMinutes(10));
        otp.setUsedYn(Constants.STATE_N);
        otpRepository.save(otp);
        sendMailSafe(req.getEmail(), "Alice Hotel OTP", "Ma OTP: " + code + " (het han 10 phut)");
    }

    @Transactional
    public void registerGuest(RegisterRequest req) {
        if (StringUtils.isAnyBlank(req.getUserId(), req.getPassword(), req.getEmail(), req.getOtp())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }
        OtpEntity otp = otpRepository.findFirstByEmailAndPurposeAndUsedYnOrderByExpireAtDesc(
                        req.getEmail(), "REGISTER", Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.BAD_REQUEST_BUSINESS));
        if (otp.getExpireAt().isBefore(LocalDateTime.now()) || !otp.getOtpCode().equals(req.getOtp())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        otp.setUsedYn(Constants.STATE_Y);
        otpRepository.save(otp);

        Query exists = entityManager.createNativeQuery("SELECT COUNT(1) FROM CPS_USER WHERE USER_ID = :uid");
        exists.setParameter("uid", req.getUserId());
        Number count = (Number) exists.getSingleResult();
        if (count.intValue() > 0) {
            throw new CustomException(ApiStatus.CONFLICT);
        }
        Query insert = entityManager.createNativeQuery(
                "INSERT INTO CPS_USER (USER_ID, USER_PW, USER_NO, USER_NAME, USER_DIV, DIVISION) VALUES (:id,:pw,:no,:name,'GUEST','CLIENT')");
        insert.setParameter("id", req.getUserId());
        insert.setParameter("pw", passwordEncoder.encode(req.getPassword()));
        insert.setParameter("no", "G" + System.currentTimeMillis() % 100000);
        insert.setParameter("name", StringUtils.defaultIfBlank(req.getUserName(), req.getUserId()));
        insert.executeUpdate();

        GuestProfileEntity profile = new GuestProfileEntity();
        profile.setUserId(req.getUserId());
        profile.setEmail(req.getEmail());
        profile.setPhone(req.getPhone());
        profile.setLockedYn(Constants.STATE_N);
        profile.setRegDate(LocalDateTime.now());
        guestProfileRepository.save(profile);

        Query roleMysql = entityManager.createNativeQuery(
                "INSERT INTO SYS_USER_ROLE (RL_ID, USER_ID, USE_YN, PUBLIC_INFO) "
                        + "SELECT '2', :uid, 'Y', 'Y' FROM (SELECT 1) t "
                        + "WHERE NOT EXISTS (SELECT 1 FROM SYS_USER_ROLE WHERE USER_ID = :uid AND RL_ID = '2')");
        roleMysql.setParameter("uid", req.getUserId());
        roleMysql.executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<GuestProfileDTO> listGuests() {
        Map<String, String> names = new HashMap<>();
        Query nameQuery = entityManager.createNativeQuery(
                "SELECT USER_ID, USER_NAME FROM CPS_USER WHERE USER_DIV = 'GUEST'");
        for (Object row : nameQuery.getResultList()) {
            Object[] cols = (Object[]) row;
            names.put(String.valueOf(cols[0]), cols[1] == null ? "" : String.valueOf(cols[1]));
        }
        return guestProfileRepository.findAllByOrderByRegDateDesc().stream().map(e -> {
            GuestProfileDTO dto = new GuestProfileDTO();
            dto.setUserId(e.getUserId());
            dto.setUserName(StringUtils.defaultIfBlank(names.get(e.getUserId()), e.getUserId()));
            dto.setEmail(e.getEmail());
            dto.setPhone(e.getPhone());
            dto.setIdNumber(e.getIdNumber());
            dto.setAddress(e.getAddress());
            dto.setPreferenceJson(e.getPreferenceJson());
            dto.setLockedYn(e.getLockedYn());
            dto.setInternalNote(e.getInternalNote());
            dto.setRegDate(e.getRegDate() == null ? null : e.getRegDate().toString().replace('T', ' ').substring(0, 16));
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public GuestProfileDTO updateGuest(GuestProfileDTO dto) {
        GuestProfileEntity e = guestProfileRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (dto.getEmail() != null) e.setEmail(dto.getEmail());
        if (dto.getPhone() != null) e.setPhone(dto.getPhone());
        if (dto.getIdNumber() != null) e.setIdNumber(dto.getIdNumber());
        if (dto.getAddress() != null) e.setAddress(dto.getAddress());
        if (dto.getPreferenceJson() != null) e.setPreferenceJson(dto.getPreferenceJson());
        if (dto.getLockedYn() != null) e.setLockedYn(dto.getLockedYn());
        if (dto.getInternalNote() != null) e.setInternalNote(dto.getInternalNote());
        e.setModDate(LocalDateTime.now());
        return modelMapper.map(guestProfileRepository.save(e), GuestProfileDTO.class);
    }

    // ---------- Review ----------
    @Transactional
    public ReviewDTO createReview(ReviewRequest req, String userId) {
        BookingEntity b = bookingRepository.findByBookingIdAndDelYn(req.getBookingId(), Constants.STATE_N)
                .orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        if (!BookingStatuses.CHECKED_OUT.equals(b.getStatus())) {
            throw new CustomException(ApiStatus.BAD_REQUEST_BUSINESS);
        }
        if (reviewRepository.findByBookingIdAndDelYn(b.getBookingId(), Constants.STATE_N).isPresent()) {
            throw new CustomException(ApiStatus.CONFLICT);
        }
        ReviewEntity r = new ReviewEntity();
        r.setReviewId(id("RV"));
        r.setBookingId(b.getBookingId());
        r.setUserId(userId);
        r.setRoomTypeId(b.getRoomTypeId());
        r.setRating(req.getRating());
        r.setComment(req.getComment());
        r.setDelYn(Constants.STATE_N);
        r.setRegDate(LocalDateTime.now());
        return modelMapper.map(reviewRepository.save(r), ReviewDTO.class);
    }

    @Transactional
    public ReviewDTO replyReview(String reviewId, String reply) {
        ReviewEntity r = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        r.setAdminReply(reply);
        return modelMapper.map(reviewRepository.save(r), ReviewDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> listReviews() {
        return reviewRepository.findByDelYnOrderByRegDateDesc(Constants.STATE_N).stream()
                .map(e -> modelMapper.map(e, ReviewDTO.class)).collect(Collectors.toList());
    }

    // ---------- Dashboard ----------
    @Transactional(readOnly = true)
    public DashboardDTO dashboard() {
        DashboardDTO dto = new DashboardDTO();
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        List<BookingEntity> all = bookingRepository.findByDelYnOrderByRegDateDesc(Constants.STATE_N).stream()
                .filter(b -> !List.of(BookingStatuses.CANCELLED, BookingStatuses.EXPIRED_HOLD).contains(b.getStatus()))
                .collect(Collectors.toList());
        dto.setRevenueToday(sumPaid(all, today, today));
        dto.setRevenueMonth(sumPaid(all, monthStart, today));
        dto.setBookingsToday(all.stream().filter(b -> b.getRegDate() != null && b.getRegDate().toLocalDate().equals(today)).count());
        dto.setBookingsMonth(all.stream().filter(b -> b.getRegDate() != null && !b.getRegDate().toLocalDate().isBefore(monthStart)).count());
        long totalRooms = roomRepository.findByDelYnOrderByRoomNumberAsc(Constants.STATE_N).stream()
                .filter(r -> Constants.STATE_Y.equals(r.getActiveYn())).count();
        long occupied = all.stream().filter(b -> BookingStatuses.CHECKED_IN.equals(b.getStatus())).count();
        dto.setOccupancyPercent(totalRooms == 0 ? 0 : (occupied * 100.0 / totalRooms));
        BigDecimal roomRevenue = all.stream()
                .filter(b -> b.getCheckIn() != null && !b.getCheckIn().isBefore(monthStart) && !b.getCheckIn().isAfter(today))
                .map(b -> nz(b.getRoomAmount())).reduce(BigDecimal.ZERO, BigDecimal::add);
        long roomNights = all.stream()
                .filter(b -> b.getCheckIn() != null && !b.getCheckIn().isBefore(monthStart))
                .mapToLong(b -> Math.max(1, ChronoUnit.DAYS.between(b.getCheckIn(), b.getCheckOut()))).sum();
        dto.setAdr(roomNights == 0 ? BigDecimal.ZERO : roomRevenue.divide(BigDecimal.valueOf(roomNights), 0, RoundingMode.HALF_UP));
        dto.setRevpar(totalRooms == 0 ? BigDecimal.ZERO : roomRevenue.divide(BigDecimal.valueOf(totalRooms * Math.max(1, ChronoUnit.DAYS.between(monthStart, today) + 1)), 0, RoundingMode.HALF_UP));

        Map<LocalDate, BigDecimal> byDay = new TreeMap<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            byDay.put(d, sumPaid(all, d, d));
        }
        byDay.forEach((d, a) -> {
            DayRevenue dr = new DayRevenue();
            dr.setDay(d);
            dr.setAmount(a);
            dto.getRevenueByDay().add(dr);
        });

        Map<String, BigDecimal> byType = new HashMap<>();
        Map<String, String> typeNames = roomTypeRepository.findByDelYnOrderByNameAsc(Constants.STATE_N).stream()
                .collect(Collectors.toMap(RoomTypeEntity::getRoomTypeId, RoomTypeEntity::getName));
        for (BookingEntity b : all) {
            if (b.getCheckIn() == null || b.getCheckIn().isBefore(monthStart)) continue;
            byType.merge(b.getRoomTypeId(), nz(b.getPaidAmount()), BigDecimal::add);
        }
        byType.forEach((id, amt) -> {
            TypeRevenue tr = new TypeRevenue();
            tr.setRoomTypeId(id);
            tr.setRoomTypeName(typeNames.getOrDefault(id, id));
            tr.setAmount(amt);
            dto.getRevenueByRoomType().add(tr);
        });

        Map<String, Long> svcCount = new HashMap<>();
        for (BookingServiceEntity line : bookingServiceItemRepository.findAll()) {
            svcCount.merge(line.getServiceId(), (long) line.getQty(), Long::sum);
        }
        dto.setTopServices(listServices(true).stream()
                .sorted((a, c) -> Long.compare(svcCount.getOrDefault(c.getServiceId(), 0L), svcCount.getOrDefault(a.getServiceId(), 0L)))
                .limit(5).collect(Collectors.toList()));
        return dto;
    }

    private BigDecimal sumPaid(List<BookingEntity> all, LocalDate from, LocalDate to) {
        return all.stream()
                .filter(b -> b.getRegDate() != null)
                .filter(b -> {
                    LocalDate d = b.getRegDate().toLocalDate();
                    return !d.isBefore(from) && !d.isAfter(to);
                })
                .map(b -> nz(b.getPaidAmount())).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ---------- Jobs ----------
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireHolds() {
        List<BookingEntity> expired = bookingRepository.findByStatusAndHoldExpireAtBeforeAndDelYn(
                BookingStatuses.PENDING_PAYMENT, LocalDateTime.now(), Constants.STATE_N);
        for (BookingEntity b : expired) {
            b.setStatus(BookingStatuses.EXPIRED_HOLD);
            b.setModDate(LocalDateTime.now());
            if (StringUtils.isNotBlank(b.getVoucherId())) {
                voucherRepository.findByVoucherIdAndDelYn(b.getVoucherId(), Constants.STATE_N).ifPresent(v -> {
                    int used = v.getUsedCount() == null ? 0 : v.getUsedCount();
                    v.setUsedCount(Math.max(0, used - 1));
                    voucherRepository.save(v);
                });
            }
            bookingRepository.save(b);
            log.info("Expired hold {}", b.getBookingCode());
        }
    }

    @Scheduled(cron = "0 0 9 * * *")
    @Transactional
    public void sendCheckInReminders() {
        LocalDate target = LocalDate.now().plusDays(1);
        for (BookingEntity b : bookingRepository.needReminder(target)) {
            sendMailSafe(b.getGuestEmail(), "Alice Hotel — Nhac check-in " + b.getBookingCode(),
                    "Ban co lich nhan phong vao " + b.getCheckIn() + ". Ma don: " + b.getBookingCode());
            b.setReminderSentYn(Constants.STATE_Y);
            bookingRepository.save(b);
        }
    }
}
