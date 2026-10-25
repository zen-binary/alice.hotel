package com.winwintek.web.backend.api.domain.admin.corporate.internCorporateSurvey.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.corporate.internCorporateSurvey.service.InternCorporateSurveyService;
import com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey.InternCorporateSurveyDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey.InternCorporateSurveyReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/a/c/intern-corporate-survey")
public class InternCorporateSurveyController {

    @Autowired
    private InternCorporateSurveyService internCorporateSurveyService;

    /**
     * Get Intern Corporate Survey
     *
     * @param internCorporateSurveyReqDTO .
     * @return InternCorporateSurveyDTO
     */
    @PostMapping ("/detail")
    public ApiResponse<InternCorporateSurveyDTO> getInternCorporateSurvey(
            @RequestBody InternCorporateSurveyReqDTO internCorporateSurveyReqDTO) {
        InternCorporateSurveyDTO res = internCorporateSurveyService.getInternCorporateSurvey(
                internCorporateSurveyReqDTO);

        return new ApiResponse<>(ApiStatus.SUCCESS, res);
    }

    /**
     * Save Intern Corporate Survey
     *
     * @param internCorporateSurveyDTO .
     * @return Boolean
     */
    @PostMapping ("/save")
    public ApiResponse<Boolean> saveInternCorporateSurvey(
            @RequestBody InternCorporateSurveyDTO internCorporateSurveyDTO) {
        Boolean res = internCorporateSurveyService.saveInternCorporateSurvey(internCorporateSurveyDTO);

        return new ApiResponse<>(ApiStatus.CREATED, res);
    }

}
