package com.winwintek.web.backend.api.domain.client.professor.internPlanLecturer.service;

import com.winwintek.web.backend.api.dto.client.professor.internPlanLecturer.InternPlanLecturerRepDTO;

public interface InternPlanLecturerService {
    InternPlanLecturerRepDTO getDetailInternPlan(String internPlanSeq);
}
