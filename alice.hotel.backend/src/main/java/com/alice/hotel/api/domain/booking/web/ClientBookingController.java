package com.alice.hotel.api.domain.booking.web;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.domain.booking.service.AliceBookingService;
import com.alice.hotel.api.domain.common.service.CommonService;
import com.alice.hotel.api.dto.booking.BookingDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/c/booking")
public class ClientBookingController {

    private final AliceBookingService bookingService;
    private final CommonService commonService;

    @GetMapping("/hotel")
    public ApiResponse<HotelDTO> hotel() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.getHotel());
    }

    @GetMapping("/room-types")
    public ApiResponse<List<RoomTypeDTO>> roomTypes() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listRoomTypes());
    }

    @GetMapping("/services")
    public ApiResponse<List<ServiceDTO>> services() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listServices(true));
    }

    @PostMapping("/availability")
    public ApiResponse<List<RoomTypeDTO>> availability(@RequestBody AvailabilityRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.searchAvailability(req));
    }

    @PostMapping("/otp")
    public ApiResponse<Boolean> otp(@RequestBody OtpRequest req) {
        bookingService.sendOtp(req);
        return new ApiResponse<>(ApiStatus.SUCCESS, true);
    }

    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody RegisterRequest req) {
        bookingService.registerGuest(req);
        return new ApiResponse<>(ApiStatus.CREATED, true);
    }

    @PostMapping
    public ApiResponse<BookingDTO> create(@RequestBody CreateBookingRequest req) {
        String userId = safeUser();
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.createBooking(req, userId));
    }

    @GetMapping("/mine")
    public ApiResponse<List<BookingDTO>> mine() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.myBookings(safeUser()));
    }

    @GetMapping("/{bookingId}")
    public ApiResponse<BookingDTO> get(@PathVariable String bookingId) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.getBooking(bookingId));
    }

    @PostMapping("/{bookingId}/cancel")
    public ApiResponse<BookingDTO> cancel(@PathVariable String bookingId, @RequestBody(required = false) CancelRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.cancelBooking(bookingId, req, safeUser()));
    }

    @PostMapping("/{bookingId}/modify")
    public ApiResponse<BookingDTO> modify(@PathVariable String bookingId, @RequestBody ModifyBookingRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.modifyBooking(bookingId, req, safeUser()));
    }

    @PostMapping("/{bookingId}/vnpay")
    public ApiResponse<VnPayCreateResponse> vnpay(@PathVariable String bookingId) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.createVnPayUrl(bookingId, safeUser()));
    }

    @GetMapping("/vnpay/return")
    public ApiResponse<BookingDTO> vnpayReturn(@RequestParam Map<String, String> params) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.confirmVnPay(params));
    }

    @PostMapping("/vnpay/ipn")
    public ApiResponse<BookingDTO> vnpayIpn(@RequestParam Map<String, String> params) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.confirmVnPay(params));
    }

    @PostMapping("/reviews")
    public ApiResponse<ReviewDTO> review(@RequestBody ReviewRequest req) {
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.createReview(req, safeUser()));
    }

    private String safeUser() {
        return commonService.getFullUserInfo().getUserId();
    }
}
