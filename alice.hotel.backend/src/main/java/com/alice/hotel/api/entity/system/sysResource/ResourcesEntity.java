package com.alice.hotel.api.entity.system.sysResource;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="sys_resource")
public class ResourcesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rsId;
    private String url;
    private String httpMethod;
    private String rsNm;
    private String rsType;
    private Date regDate;

}
