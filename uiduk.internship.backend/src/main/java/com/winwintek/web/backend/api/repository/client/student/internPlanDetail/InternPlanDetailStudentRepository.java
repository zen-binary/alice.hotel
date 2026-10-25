package com.winwintek.web.backend.api.repository.client.student.internPlanDetail;


import com.winwintek.web.backend.api.entity.client.student.internPlanDetail.IInternPlanDetailOutput;
import com.winwintek.web.backend.api.entity.client.student.internPlanDetail.InternPlanDetailStdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternPlanDetailStudentRepository extends JpaRepository<InternPlanDetailStdEntity, String> {
    @Query(value = """
        SELECT
            IPD.WEEK AS week,
            IPD.START_DATE AS startDate,
            IPD.END_DATE AS endDate,
            IPD.CONTENT AS content
        FROM
            INTERN_PLAN_DETAIL ipd
        WHERE
        DEL_YN = :delYn AND
        IPD.INTERN_PLAN_SEQ = :id
    """, nativeQuery = true)
    List<IInternPlanDetailOutput> getInternPlanDetail(@Param("id") String id, @Param("delYn") String delYn);

}
