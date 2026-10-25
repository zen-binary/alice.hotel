package com.winwintek.web.backend.api.repository.common;

import com.winwintek.web.backend.api.entity.common.FileMngEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMngRepository extends JpaRepository<FileMngEntity, Long> {

    List<FileMngEntity> findByFimReferKeyId(String fimReferKeyId);

    FileMngEntity findByFimFileNameAndDelYn(String fimFileName, String delYn);

    List<FileMngEntity> findByFimReferKeyIdAndFimFileCategoryAndFimSectionNameAndDelYn(String fimReferKeyId,
            String fimFileCategory, String fimSectionName, String delYn);

    List<FileMngEntity> findByFimReferKeyIdInAndFimSectionNameInAndDelYn(List<String> referKeys,
            List<String> sectionNames, String delYn);

}
