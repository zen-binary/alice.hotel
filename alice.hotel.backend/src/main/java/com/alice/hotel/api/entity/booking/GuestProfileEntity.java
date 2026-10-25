package com.alice.hotel.api.entity.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ah_guest_profile")
@Getter
@Setter
public class GuestProfileEntity {
    @Id @Column(name = "user_id") private String userId;
    private String email;
    private String phone;
    @Column(name = "id_number") private String idNumber;
    private String address;
    @Column(name = "preference_json", length = 1000) private String preferenceJson;
    @Column(name = "locked_yn") private String lockedYn;
    @Column(name = "internal_note", length = 1000) private String internalNote;
    @Column(name = "reg_date") private LocalDateTime regDate;
    @Column(name = "mod_date") private LocalDateTime modDate;
}
