package com.winwintek.web.backend.api.entity.client.student.agreeTerm;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table(name = "SYS_USER_ROLE")
@Data
public class AgreeTermUserEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long urId;
    private String rlId;
    private String userId;
    private String useYn;
    private String publicInfo;
}
