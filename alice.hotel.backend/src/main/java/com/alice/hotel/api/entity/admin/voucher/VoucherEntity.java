package com.alice.hotel.api.entity.admin.voucher;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;
import java.time.LocalDate;

@DynamicUpdate
@Entity
@Table(name = "ah_voucher")
@Getter
@Setter
public class VoucherEntity {
    @Id @Column(name = "voucher_id") private String voucherId;
    private String code;
    private String name;
    @Column(name = "discount_type") private String discountType;
    @Column(name = "discount_value") private BigDecimal discountValue;
    @Column(name = "max_discount") private BigDecimal maxDiscount;
    @Column(name = "min_spend") private BigDecimal minSpend;
    @Column(name = "start_date") private LocalDate startDate;
    @Column(name = "end_date") private LocalDate endDate;
    @Column(name = "usage_limit") private Integer usageLimit;
    @Column(name = "used_count") private Integer usedCount;
    @Column(name = "active_yn") private String activeYn;
    @Column(name = "del_yn") private String delYn;
}
