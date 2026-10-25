package com.winwintek.web.backend.api.entity.client.student.dashboard.docRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table (name = "NOTICE")
@Data
public class DashboardDocRoomEntity {

    @Id
    private String docSeq;
    private String title;
    private String content;
    private String topFixYn;
    private String delYn;
}
