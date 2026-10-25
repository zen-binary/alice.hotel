package com.alice.hotel.api.entity.admin.rate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;
import java.time.LocalDate;

@DynamicUpdate
@Entity
@Table(name = "ah_rate")
@Getter
@Setter
public class RateEntity {
    @Id @Column(name = "rate_id") private String rateId;
    @Column(name = "room_type_id") private String roomTypeId;
    @Column(name = "start_date") private LocalDate startDate;
    @Column(name = "end_date") private LocalDate endDate;
    private BigDecimal price;
    @Column(name = "dow_mask") private String dowMask;
    @Column(name = "stop_sell_yn") private String stopSellYn;
    private String name;
    @Column(name = "del_yn") private String delYn;
}
