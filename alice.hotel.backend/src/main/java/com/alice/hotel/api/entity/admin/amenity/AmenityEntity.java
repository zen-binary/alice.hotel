package com.alice.hotel.api.entity.admin.amenity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table(name = "ah_amenity")
@Getter
@Setter
public class AmenityEntity {
    @Id @Column(name = "amenity_id") private String amenityId;
    private String code;
    private String name;
    private String icon;
    @Column(name = "del_yn") private String delYn;
}
