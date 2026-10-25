package com.alice.hotel.api.dto.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class BookingDtos {
    private BookingDtos() {}

    @Data
    public static class HotelDTO {
        private String hotelId;
        private String name;
        private String address;
        private String phone;
        private String email;
        private String description;
        private String checkInTime;
        private String checkOutTime;
        private Integer cancelFreeHours;
        private Integer cancelRefundPercent;
        private Integer depositPercent;
        private BigDecimal earlyCheckinFee;
        private BigDecimal lateCheckoutFee;
        private Integer holdMinutes;
        private String imageUrl;
    }

    @Data
    public static class RoomTypeDTO {
        private String roomTypeId;
        private String code;
        private String name;
        private String description;
        private BigDecimal basePrice;
        private Integer capacityAdult;
        private Integer capacityChild;
        private String bedInfo;
        private String imageUrls;
        private Long availableRooms;
        private BigDecimal estimatedTotal;
        private Double avgRating;
    }

    @Data
    public static class RoomDTO {
        private String roomId;
        private String roomTypeId;
        private String roomNumber;
        private Integer floorNo;
        private String houseStatus;
        private String activeYn;
        private String note;
        private String roomTypeName;
    }

    @Data
    public static class ServiceDTO {
        private String serviceId;
        private String code;
        private String name;
        private String category;
        private BigDecimal unitPrice;
        private String unitName;
        private String activeYn;
    }

    @Data
    public static class VoucherDTO {
        private String voucherId;
        private String code;
        private String name;
        private String discountType;
        private BigDecimal discountValue;
        private BigDecimal maxDiscount;
        private BigDecimal minSpend;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer usageLimit;
        private Integer usedCount;
        private String activeYn;
    }

    @Data
    public static class ServiceLineRequest {
        private String serviceId;
        private Integer qty;
        private String note;
    }

    @Data
    public static class CreateBookingRequest {
        private String roomTypeId;
        private String roomId;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private Integer adults;
        private Integer children;
        private String guestName;
        private String guestPhone;
        private String guestEmail;
        private String guestIdNumber;
        private String voucherCode;
        private String note;
        private String source;
        private Boolean payAtHotel;
        private List<ServiceLineRequest> services = new ArrayList<>();
    }

    @Data
    public static class BookingDTO {
        private String bookingId;
        private String bookingCode;
        private String userId;
        private String guestName;
        private String guestPhone;
        private String guestEmail;
        private String guestIdNumber;
        private String roomTypeId;
        private String roomTypeName;
        private String roomId;
        private String roomNumber;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private Integer adults;
        private Integer children;
        private String status;
        private String source;
        private LocalDateTime holdExpireAt;
        private BigDecimal roomAmount;
        private BigDecimal serviceAmount;
        private BigDecimal discountAmount;
        private BigDecimal feeAmount;
        private BigDecimal totalAmount;
        private BigDecimal paidAmount;
        private BigDecimal remainingAmount;
        private BigDecimal refundEstimate;
        private String voucherId;
        private String cancelReason;
        private String note;
        private List<ServiceLineDTO> services = new ArrayList<>();
        private List<PaymentDTO> payments = new ArrayList<>();
    }

    @Data
    public static class ServiceLineDTO {
        private Long id;
        private String serviceId;
        private String serviceName;
        private Integer qty;
        private BigDecimal unitPrice;
        private BigDecimal amount;
        private String note;
    }

    @Data
    public static class PaymentDTO {
        private String paymentId;
        private String method;
        private BigDecimal amount;
        private String status;
        private String txnRef;
        private LocalDateTime regDate;
        private String note;
    }

    @Data
    public static class AvailabilityRequest {
        private LocalDate checkIn;
        private LocalDate checkOut;
        private Integer adults;
        private Integer children;
        private String roomTypeId;
    }

    @Data
    public static class PaymentRequest {
        private String bookingId;
        private String method;
        private BigDecimal amount;
        private String note;
    }

    @Data
    public static class CancelRequest {
        private String reason;
    }

    @Data
    public static class ModifyBookingRequest {
        private LocalDate checkIn;
        private LocalDate checkOut;
        private String roomTypeId;
    }

    @Data
    public static class CheckInRequest {
        private String roomId;
        private String guestIdNumber;
        private Boolean earlyCheckIn;
        /** Allow check-in when room is not VACANT_CLEAN (audited). */
        private Boolean forceDirtyOverride;
        private String overrideReason;
    }

    @Data
    public static class RateDTO {
        private String rateId;
        private String roomTypeId;
        private String roomTypeName;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal price;
        private String dowMask;
        private String stopSellYn;
        private String name;
    }

    @Data
    public static class TimelineRequest {
        private LocalDate from;
        private LocalDate to;
    }

    @Data
    public static class TimelineBooking {
        private String bookingId;
        private String bookingCode;
        private String roomId;
        private String roomNumber;
        private String guestName;
        private String status;
        private LocalDate checkIn;
        private LocalDate checkOut;
    }

    @Data
    public static class CheckOutRequest {
        private Boolean lateCheckOut;
        private String method;
        private BigDecimal amount;
    }

    @Data
    public static class RoomMoveRequest {
        private String newRoomId;
    }

    @Data
    public static class HouseStatusRequest {
        private String houseStatus;
    }

    @Data
    public static class ShiftOpenRequest {
        private BigDecimal openingCash;
        private String note;
    }

    @Data
    public static class ShiftCloseRequest {
        private BigDecimal closingCash;
        private String note;
    }

    @Data
    public static class ShiftDTO {
        private String shiftId;
        private String userId;
        private LocalDateTime openedAt;
        private LocalDateTime closedAt;
        private BigDecimal openingCash;
        private BigDecimal closingCash;
        private BigDecimal expectedCash;
        private BigDecimal cashTotal;
        private BigDecimal transferTotal;
        private BigDecimal onlineTotal;
        private String note;
        private String status;
    }

    @Data
    public static class ReviewRequest {
        private String bookingId;
        private Integer rating;
        private String comment;
    }

    @Data
    public static class ReviewDTO {
        private String reviewId;
        private String bookingId;
        private String userId;
        private String roomTypeId;
        private Integer rating;
        private String comment;
        private String adminReply;
        private LocalDateTime regDate;
    }

    @Data
    public static class RegisterRequest {
        private String userId;
        private String password;
        private String userName;
        private String email;
        private String phone;
        private String otp;
    }

    @Data
    public static class OtpRequest {
        private String email;
        private String purpose;
    }

    @Data
    public static class DashboardDTO {
        private BigDecimal revenueToday;
        private BigDecimal revenueMonth;
        private long bookingsToday;
        private long bookingsMonth;
        private double occupancyPercent;
        private BigDecimal adr;
        private BigDecimal revpar;
        private List<DayRevenue> revenueByDay = new ArrayList<>();
        private List<TypeRevenue> revenueByRoomType = new ArrayList<>();
        private List<ServiceDTO> topServices = new ArrayList<>();
    }

    @Data
    public static class DayRevenue {
        private LocalDate day;
        private BigDecimal amount;
    }

    @Data
    public static class TypeRevenue {
        private String roomTypeId;
        private String roomTypeName;
        private BigDecimal amount;
    }

    @Data
    public static class FrontDeskBoard {
        private List<RoomDTO> rooms = new ArrayList<>();
        private List<BookingDTO> arrivals = new ArrayList<>();
        private List<BookingDTO> departures = new ArrayList<>();
        private List<BookingDTO> inHouse = new ArrayList<>();
        private List<BookingDTO> dirtyQueue = new ArrayList<>();
    }

    @Data
    public static class VnPayCreateResponse {
        private String paymentUrl;
        private String txnRef;
        private BigDecimal amount;
    }

    @Data
    public static class GuestProfileDTO {
        private String userId;
        private String email;
        private String phone;
        private String idNumber;
        private String address;
        private String preferenceJson;
        private String lockedYn;
        private String internalNote;
        private String userName;
        private String regDate;
    }
}
