package com.winwintek.web.backend.api.entity.common;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table(name = "FILE_MNG")
@Getter
@Setter
@Data
public class FileMngEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String fimId;
    private String fimSubFileId;
    private String fimFileCategory;
    private String fimFileName;
    private String fimFilePath;
    private String fimFileExt;
    private Long fimFileSize;
    private String fimUseYn;
    private String fimReferKeyId;
    private String fimFileOrgName;
    private String fimSectionName;
    private String delYn;
}
