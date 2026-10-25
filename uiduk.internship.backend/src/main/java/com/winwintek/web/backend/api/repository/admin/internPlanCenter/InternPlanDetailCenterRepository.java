package com.winwintek.web.backend.api.repository.admin.internPlanCenter;

import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanDetailCenterMng;
import com.winwintek.web.backend.api.entity.admin.internPlanCenter.InternPlanDetailListCenterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternPlanDetailCenterRepository extends JpaRepository<InternPlanDetailCenterMng, String> {

    @Query (value = """
                        SELECT
                            INTERN_DETAIL_SEQ,
                            WEEK,
                            START_DATE,
                            END_DATE,
                            CONTENT,
                            DEL_YN
                        FROM INTERN_PLAN_DETAIL
                        WHERE INTERN_PLAN_SEQ = :seq
                        AND DEL_YN = :delYn
                        ORDER BY WEEK ASC
            """, nativeQuery = true)
    List<InternPlanDetailListCenterResponse> getListDetail(@Param ("seq") String seq, @Param ("delYn") String delYn);
}
