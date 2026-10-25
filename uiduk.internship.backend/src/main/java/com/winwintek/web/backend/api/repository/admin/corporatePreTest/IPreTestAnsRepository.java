package com.winwintek.web.backend.api.repository.admin.corporatePreTest;

import com.winwintek.web.backend.api.entity.admin.corporatePreTest.PreTestAnsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IPreTestAnsRepository extends JpaRepository<PreTestAnsEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE PRE_TEST_ANS
        SET DEL_YN = :stsDelY
        WHERE CORPORATE_PRE_TEST_SEQ = :corporatePreTestSeq
    """, nativeQuery = true)
    void updateDelAll(@Param("corporatePreTestSeq") String corporatePreTestSeq,
                      @Param("stsDelY")  String stsDelY);
}
