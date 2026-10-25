package com.alice.hotel.api.entity.system;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table (name = "V_STD_CDP_SREG")
@Getter
@Setter
public class VSregEntity {

    @Id
    private String stdNo;

    private String pw;

    private String korNm;

    private String userDiv;

}
