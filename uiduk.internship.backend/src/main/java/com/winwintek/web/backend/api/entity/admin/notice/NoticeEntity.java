package com.winwintek.web.backend.api.entity.admin.notice;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@DynamicUpdate
@Entity
@Table (name = "NOTICE")
@Data
public class NoticeEntity extends AbstractAuditingEntity implements Serializable {

    @Id
    private String noticeSeq;

    private String title;

    @Lob
    private String content;

    private Date startDateShow;

    private Date endDateShow;

    private String useYn;

    private String topFixYn;

    private String delYn;

}
