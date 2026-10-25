package com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name="DOC_ROOM")
@Data
public class DocumentRoomSupportCenterEntity extends AbstractAuditingEntity<String> implements Serializable {
    @Id
//    @Column (name = "DOC_SEQ")
    private String docSeq;

//    @Column(name = "TITLE")
    private String title;

//    @Column(name = "CONTENT")
    private String content;

//    @Column(name = "TOP_FIX_YN")
    private String topFixYn;

//    @Column(name = "DEL_YN")
    private String delYn;

    private String useYn;
}
