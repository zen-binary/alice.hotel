package com.alice.hotel.api.domain.booking;

public final class BookingStatuses {
    private BookingStatuses() {}
    public static final String DRAFT_HOLD = "DRAFT_HOLD";
    public static final String PENDING_PAYMENT = "PENDING_PAYMENT";
    public static final String CONFIRMED = "CONFIRMED";
    public static final String CHECKED_IN = "CHECKED_IN";
    public static final String CHECKED_OUT = "CHECKED_OUT";
    public static final String CANCELLED = "CANCELLED";
    public static final String NO_SHOW = "NO_SHOW";
    public static final String EXPIRED_HOLD = "EXPIRED_HOLD";
}
