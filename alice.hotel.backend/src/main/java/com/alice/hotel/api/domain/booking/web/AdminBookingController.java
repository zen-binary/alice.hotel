package com.alice.hotel.api.domain.booking.web;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.domain.booking.service.AliceBookingService;
import com.alice.hotel.api.domain.common.service.CommonService;
import com.alice.hotel.api.dto.booking.BookingDtos.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/a")
public class AdminBookingController {

    private final AliceBookingService bookingService;
    private final CommonService commonService;

    private String user() {
        return commonService.getFullUserInfo().getUserId();
    }

    @GetMapping("/hotel")
    public ApiResponse<HotelDTO> getHotel() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.getHotel());
    }

    @PutMapping("/hotel")
    public ApiResponse<HotelDTO> saveHotel(@RequestBody HotelDTO dto) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.saveHotel(dto));
    }

    @PostMapping("/room-types/list")
    public ApiResponse<List<RoomTypeDTO>> roomTypes() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listRoomTypes());
    }

    @PostMapping("/room-types")
    public ApiResponse<RoomTypeDTO> createRoomType(@RequestBody RoomTypeDTO dto) {
        dto.setRoomTypeId(null);
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.saveRoomType(dto));
    }

    @PutMapping("/room-types/{id}")
    public ApiResponse<RoomTypeDTO> updateRoomType(@PathVariable String id, @RequestBody RoomTypeDTO dto) {
        dto.setRoomTypeId(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.saveRoomType(dto));
    }

    @DeleteMapping("/room-types/{id}")
    public ApiResponse<Boolean> deleteRoomType(@PathVariable String id) {
        bookingService.deleteRoomType(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, true);
    }

    @PostMapping("/rooms/list")
    public ApiResponse<List<RoomDTO>> rooms() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listRooms());
    }

    @PostMapping("/rooms")
    public ApiResponse<RoomDTO> createRoom(@RequestBody RoomDTO dto) {
        dto.setRoomId(null);
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.saveRoom(dto));
    }

    @PutMapping("/rooms/{id}")
    public ApiResponse<RoomDTO> updateRoom(@PathVariable String id, @RequestBody RoomDTO dto) {
        dto.setRoomId(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.saveRoom(dto));
    }

    @PutMapping("/rooms/{id}/house-status")
    public ApiResponse<RoomDTO> houseStatus(@PathVariable String id, @RequestBody HouseStatusRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.updateHouseStatus(id, req, user()));
    }

    @PostMapping("/hotel-services/list")
    public ApiResponse<List<ServiceDTO>> services() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listServices(false));
    }

    @PostMapping("/hotel-services")
    public ApiResponse<ServiceDTO> createService(@RequestBody ServiceDTO dto) {
        dto.setServiceId(null);
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.saveService(dto));
    }

    @PutMapping("/hotel-services/{id}")
    public ApiResponse<ServiceDTO> updateService(@PathVariable String id, @RequestBody ServiceDTO dto) {
        dto.setServiceId(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.saveService(dto));
    }

    @PostMapping("/vouchers/list")
    public ApiResponse<List<VoucherDTO>> vouchers() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listVouchers());
    }

    @PostMapping("/vouchers")
    public ApiResponse<VoucherDTO> createVoucher(@RequestBody VoucherDTO dto) {
        dto.setVoucherId(null);
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.saveVoucher(dto));
    }

    @PutMapping("/vouchers/{id}")
    public ApiResponse<VoucherDTO> updateVoucher(@PathVariable String id, @RequestBody VoucherDTO dto) {
        dto.setVoucherId(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.saveVoucher(dto));
    }

    @PostMapping("/bookings/list")
    public ApiResponse<List<BookingDTO>> bookings(@RequestBody(required = false) MapSearch search) {
        String status = search == null ? null : search.status;
        LocalDate from = search == null ? null : search.fromDate;
        LocalDate to = search == null ? null : search.toDate;
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.adminBookings(status, from, to));
    }

    @PostMapping("/bookings")
    public ApiResponse<BookingDTO> walkIn(@RequestBody CreateBookingRequest req) {
        req.setSource("WALK_IN");
        req.setPayAtHotel(true);
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.createBooking(req, user()));
    }

    @GetMapping("/bookings/{id}")
    public ApiResponse<BookingDTO> getBooking(@PathVariable String id) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.getBooking(id));
    }

    @PostMapping("/bookings/{id}/check-in")
    public ApiResponse<BookingDTO> checkIn(@PathVariable String id, @RequestBody(required = false) CheckInRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.checkIn(id, req == null ? new CheckInRequest() : req, user()));
    }

    @PostMapping("/bookings/{id}/check-out")
    public ApiResponse<BookingDTO> checkOut(@PathVariable String id, @RequestBody(required = false) CheckOutRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.checkOut(id, req == null ? new CheckOutRequest() : req, user()));
    }

    @PostMapping("/bookings/{id}/move")
    public ApiResponse<BookingDTO> move(@PathVariable String id, @RequestBody RoomMoveRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.roomMove(id, req, user()));
    }

    @PostMapping("/bookings/{id}/assign-room")
    public ApiResponse<BookingDTO> assignRoom(@PathVariable String id, @RequestBody RoomMoveRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.roomMove(id, req, user()));
    }

    @PostMapping("/bookings/{id}/modify")
    public ApiResponse<BookingDTO> modify(@PathVariable String id, @RequestBody ModifyBookingRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.modifyBooking(id, req, user()));
    }

    @PostMapping("/bookings/{id}/services")
    public ApiResponse<BookingDTO> addService(@PathVariable String id, @RequestBody ServiceLineRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.addService(id, req, user()));
    }

    @PostMapping("/bookings/{id}/pay")
    public ApiResponse<BookingDTO> pay(@PathVariable String id, @RequestBody PaymentRequest req) {
        req.setBookingId(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.counterPayment(req, user()));
    }

    @PostMapping("/bookings/{id}/cancel")
    public ApiResponse<BookingDTO> cancel(@PathVariable String id, @RequestBody(required = false) CancelRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.cancelBooking(id, req, user()));
    }

    @PostMapping("/bookings/{id}/no-show")
    public ApiResponse<BookingDTO> noShow(@PathVariable String id) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.markNoShow(id, user()));
    }

    @GetMapping("/front-desk/board")
    public ApiResponse<FrontDeskBoard> board() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.frontDeskBoard());
    }

    @GetMapping("/front-desk/timeline")
    public ApiResponse<List<TimelineBooking>> timeline(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.timeline(from, to));
    }

    @PostMapping("/rates/list")
    public ApiResponse<List<RateDTO>> rates(@RequestBody(required = false) RateDTO filter) {
        String typeId = filter == null ? null : filter.getRoomTypeId();
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listRates(typeId));
    }

    @PostMapping("/rates")
    public ApiResponse<RateDTO> createRate(@RequestBody RateDTO dto) {
        dto.setRateId(null);
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.saveRate(dto));
    }

    @PutMapping("/rates/{id}")
    public ApiResponse<RateDTO> updateRate(@PathVariable String id, @RequestBody RateDTO dto) {
        dto.setRateId(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.saveRate(dto));
    }

    @DeleteMapping("/rates/{id}")
    public ApiResponse<Boolean> deleteRate(@PathVariable String id) {
        bookingService.deleteRate(id);
        return new ApiResponse<>(ApiStatus.SUCCESS, true);
    }

    @PostMapping("/shifts/open")
    public ApiResponse<ShiftDTO> openShift(@RequestBody ShiftOpenRequest req) {
        return new ApiResponse<>(ApiStatus.CREATED, bookingService.openShift(req, user()));
    }

    @PostMapping("/shifts/close")
    public ApiResponse<ShiftDTO> closeShift(@RequestBody ShiftCloseRequest req) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.closeShift(req, user()));
    }

    @GetMapping("/shifts/current")
    public ApiResponse<ShiftDTO> currentShift() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.currentShift(user()));
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardDTO> dashboard() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.dashboard());
    }

    @GetMapping("/guests")
    public ApiResponse<List<GuestProfileDTO>> guests() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listGuests());
    }

    @PutMapping("/guests/{userId}")
    public ApiResponse<GuestProfileDTO> updateGuest(@PathVariable String userId, @RequestBody GuestProfileDTO dto) {
        dto.setUserId(userId);
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.updateGuest(dto));
    }

    @GetMapping("/reviews")
    public ApiResponse<List<ReviewDTO>> reviews() {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.listReviews());
    }

    @PostMapping("/reviews/{id}/reply")
    public ApiResponse<ReviewDTO> reply(@PathVariable String id, @RequestBody CancelRequest body) {
        return new ApiResponse<>(ApiStatus.SUCCESS, bookingService.replyReview(id, body == null ? null : body.getReason()));
    }

    @GetMapping("/reports/excel")
    public ResponseEntity<byte[]> excel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) throws Exception {
        List<BookingDTO> list = bookingService.adminBookings(null, from, to);
        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("bookings");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Code");
            header.createCell(1).setCellValue("Guest");
            header.createCell(2).setCellValue("CheckIn");
            header.createCell(3).setCellValue("CheckOut");
            header.createCell(4).setCellValue("Status");
            header.createCell(5).setCellValue("Total");
            header.createCell(6).setCellValue("Paid");
            int i = 1;
            for (BookingDTO b : list) {
                Row row = sheet.createRow(i++);
                row.createCell(0).setCellValue(b.getBookingCode());
                row.createCell(1).setCellValue(b.getGuestName());
                row.createCell(2).setCellValue(String.valueOf(b.getCheckIn()));
                row.createCell(3).setCellValue(String.valueOf(b.getCheckOut()));
                row.createCell(4).setCellValue(b.getStatus());
                row.createCell(5).setCellValue(b.getTotalAmount() == null ? 0 : b.getTotalAmount().doubleValue());
                row.createCell(6).setCellValue(b.getPaidAmount() == null ? 0 : b.getPaidAmount().doubleValue());
            }
            wb.write(out);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=alice-bookings.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(out.toByteArray());
        }
    }

    public static class MapSearch {
        public String status;
        public LocalDate fromDate;
        public LocalDate toDate;
    }
}
