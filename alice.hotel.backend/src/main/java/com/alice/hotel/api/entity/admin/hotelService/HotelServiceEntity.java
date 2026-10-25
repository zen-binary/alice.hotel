package com.alice.hotel.api.entity.admin.hotelService;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;

@DynamicUpdate
@Entity
@Table(name = "ah_service")
@Getter
@Setter
public class HotelServiceEntity {
    @Id @Column(name = "service_id") private String serviceId;
    private String code;
    private String name;
    private String category;
    @Column(name = "unit_price") private BigDecimal unitPrice;
    @Column(name = "unit_name") private String unitName;
    @Column(name = "active_yn") private String activeYn;
    @Column(name = "del_yn") private String delYn;
}
