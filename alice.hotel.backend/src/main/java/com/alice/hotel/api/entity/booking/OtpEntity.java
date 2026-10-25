package com.alice.hotel.api.entity.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ah_otp")
@Getter
@Setter
public class OtpEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id") private Long otpId;
    private String email;
    @Column(name = "otp_code") private String otpCode;
    private String purpose;
    @Column(name = "expire_at") private LocalDateTime expireAt;
    @Column(name = "used_yn") private String usedYn;
}
