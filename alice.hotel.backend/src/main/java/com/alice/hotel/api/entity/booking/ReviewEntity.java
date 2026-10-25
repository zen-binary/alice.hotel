package com.alice.hotel.api.entity.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ah_review")
@Getter
@Setter
public class ReviewEntity {
    @Id @Column(name = "review_id") private String reviewId;
    @Column(name = "booking_id") private String bookingId;
    @Column(name = "user_id") private String userId;
    @Column(name = "room_type_id") private String roomTypeId;
    private Integer rating;
    @Column(length = 2000) private String comment;
    @Column(name = "admin_reply", length = 2000) private String adminReply;
    @Column(name = "del_yn") private String delYn;
    @Column(name = "reg_date") private LocalDateTime regDate;
}
