package com.winwintek.web.backend.api.repository.admin.staffIntern;

import com.winwintek.web.backend.api.entity.admin.staffIntern.StaffInternEntity;
import com.winwintek.web.backend.api.entity.admin.staffIntern.StaffInternResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffInternRepository extends JpaRepository<StaffInternEntity, String> {

    @Query (value = """
                        SELECT
                            STAFF_NO AS staffNo,
                            KOR_NM AS korNm
                            FROM V_STD_CDP_STAF
            """, nativeQuery = true)
    List<StaffInternResponse> getListStaff();

}
