package com.winwintek.web.backend.api.repository.admin.internPlanDetail;

import com.winwintek.web.backend.api.entity.admin.internPlanDetail.InternPlanDetailEntity;
import com.winwintek.web.backend.api.entity.admin.internPlanDetail.InternPlanListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternPlanDetailRepository extends JpaRepository<InternPlanDetailEntity, String> {

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
    List<InternPlanListResponse> getListDetail(@Param ("seq") String seq, @Param ("delYn") String delYn);

}
