package com.winwintek.web.backend.api.domain.admin.corporate.internCorporateSurvey.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.corporate.internCorporateSurvey.service.InternCorporateSurveyService;
import com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey.CorporateSurveyQusDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey.InternCorporateSurveyDTO;
import com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey.InternCorporateSurveyReqDTO;
import com.winwintek.web.backend.api.entity.admin.cmnCorporateMembers.CmmCorporateMembersEntity;
import com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity.CorporateSurveyAnsEntity;
import com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity.CorporateSurveyQusEntity;
import com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity.InternCorporateSurveyEntity;
import com.winwintek.web.backend.api.entity.admin.internPlan.InternPlanEntity;
import com.winwintek.web.backend.api.repository.admin.cmmCorporateMember.CmmCorporateMembersRepository;
import com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey.CorporateSurveyAnsRepository;
import com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey.CorporateSurveyQusRepository;
import com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey.InternCorporateSurveyRepository;
import com.winwintek.web.backend.api.repository.admin.corporate.internCorporateSurvey.InternPlanStdCorpRepository;
import com.winwintek.web.backend.api.repository.admin.internPlan.InternPlanRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_CORPORATE_SURVEY_ANS;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_INTERN_CORPORATE_SURVEY;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_CORPORATE_SURVEY_ANS;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_INTERN_CORPORATE_SURVEY;

@Service
public class InternCorporateSurveyServiceImpl implements InternCorporateSurveyService {

    @Autowired
    private InternCorporateSurveyRepository internCorporateSurveyRepository;

    @Autowired
    private CorporateSurveyQusRepository corporateSurveyQusRepository;

    @Autowired
    private CorporateSurveyAnsRepository corporateSurveyAnsRepository;

    @Autowired
    private InternPlanRepository internPlanRepository;

    @Autowired
    private CmmCorporateMembersRepository cmmCorporateMembersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Override
    public InternCorporateSurveyDTO getInternCorporateSurvey(InternCorporateSurveyReqDTO internCorporateSurveyReqDTO) {
        Optional<InternPlanEntity> internPlanEntityFind = internPlanRepository.findById(
                internCorporateSurveyReqDTO.getInternPlanSeq());
        if (! internPlanEntityFind.isPresent()) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }

        InternCorporateSurveyDTO internCorporateSurveyDTO = new InternCorporateSurveyDTO();
        internCorporateSurveyDTO.setInternPlanSeq(internPlanEntityFind.get().getInternPlanSeq());

        Optional<CmmCorporateMembersEntity> cmmCorporateMembers = cmmCorporateMembersRepository.findById(
                internPlanEntityFind.get().getCormSeq());
        if (! cmmCorporateMembers.isPresent()) {
            throw new CustomException(ApiStatus.NOT_FOUND);
        }

        internCorporateSurveyDTO.setCormNm(cmmCorporateMembers.get().getCormNameKr());

        internCorporateSurveyDTO.setInternQuantity(internPlanEntityFind.get().getInternRecruitmentQuantity());
        internCorporateSurveyDTO.setInternshipTerm(internPlanEntityFind.get().getInternTerm());

        InternCorporateSurveyEntity internCorporateSurveyEntity = internCorporateSurveyRepository.findByInternPlanSeqAndDelYn(
                internCorporateSurveyReqDTO.getInternPlanSeq(), Constants.STATE_N);

        if (Objects.isNull(internCorporateSurveyEntity)) {
            internCorporateSurveyDTO.setInternRecommend("");
        } else {
            internCorporateSurveyDTO.setInternCorporateSurveySeq(
                    internCorporateSurveyEntity.getInternCorporateSurveySeq());
            internCorporateSurveyDTO.setInternRecommend(internCorporateSurveyEntity.getInternRecommend());
        }

        List<CorporateSurveyQusEntity> listQusEntity = corporateSurveyQusRepository.findAllByDelYnOrderByDisplayOrder(
                Constants.STATE_N);

        List<CorporateSurveyQusDTO> listQus = listQusEntity.stream()
                .map(entity -> modelMapper.map(entity, CorporateSurveyQusDTO.class)).toList();

        if (Objects.nonNull(internCorporateSurveyEntity)) {
            List<CorporateSurveyAnsEntity> listCorporateSurveyAnsEntity = corporateSurveyAnsRepository.findAllByInternCorporateSurveySeqAndDelYn(
                    internCorporateSurveyEntity.getInternCorporateSurveySeq(), Constants.STATE_N);

            for (CorporateSurveyQusDTO corporateSurveyQusDTO : listQus) {

                CorporateSurveyAnsEntity corporateSurveyAnsEntity = this.findCorporateSurveyAnsEntity(
                        listCorporateSurveyAnsEntity, corporateSurveyQusDTO.getCorporateSurveyQusSeq());
                if (Objects.nonNull(corporateSurveyAnsEntity)) {
                    corporateSurveyQusDTO.setCorporateSurveyAnsSeq(corporateSurveyAnsEntity.getCorporateSurveyAnsSeq());
                    corporateSurveyQusDTO.setScore(String.valueOf(corporateSurveyAnsEntity.getScore()));
                }

            }
        }

        internCorporateSurveyDTO.setListQus(listQus);

        return internCorporateSurveyDTO;
    }

    private CorporateSurveyAnsEntity findCorporateSurveyAnsEntity(
            List<CorporateSurveyAnsEntity> listCorporateSurveyAnsEntity, String qusSeq) {
        return listCorporateSurveyAnsEntity.stream()
                .filter(ansEntity -> ansEntity.getCorporateSurveyQusSeq().equals(qusSeq)).findFirst().orElse(null);
    }

    @Override
    @Transactional
    public Boolean saveInternCorporateSurvey(InternCorporateSurveyDTO internCorporateSurveyDTO) {
        InternCorporateSurveyEntity internCorporateSurveyEntity = new InternCorporateSurveyEntity();
        boolean isNew = StringUtils.isBlank(internCorporateSurveyDTO.getInternCorporateSurveySeq());

        if (isNew) {

            internCorporateSurveyEntity.setInternCorporateSurveySeq(
                    sequenceGenerator.getNextSequence(PREFIX_INTERN_CORPORATE_SURVEY.toString(),
                            TABLE_INTERN_CORPORATE_SURVEY.toString()));

        } else {

            Optional<InternCorporateSurveyEntity> internCorporateSurveyEntityFind = internCorporateSurveyRepository.findById(
                    internCorporateSurveyDTO.getInternCorporateSurveySeq());
            if (! internCorporateSurveyEntityFind.isPresent()) {
                throw new CustomException(ApiStatus.NOT_FOUND);
            }
            internCorporateSurveyEntity = internCorporateSurveyEntityFind.get();

        }

        internCorporateSurveyEntity.setInternPlanSeq(internCorporateSurveyDTO.getInternPlanSeq());
        internCorporateSurveyEntity.setInternRecommend(internCorporateSurveyDTO.getInternRecommend());
        internCorporateSurveyEntity.setDelYn(Constants.STATE_N);

        InternCorporateSurveyEntity internCorporateSurveyEntitySave = internCorporateSurveyRepository.save(
                internCorporateSurveyEntity);

        List<CorporateSurveyAnsEntity> listAnsEntity = new ArrayList<>();
        for (CorporateSurveyQusDTO corporateSurveyQusDTO : internCorporateSurveyDTO.getListQus()) {
            CorporateSurveyAnsEntity corporateSurveyAnsEntity = new CorporateSurveyAnsEntity();

            if (StringUtils.isBlank(corporateSurveyQusDTO.getCorporateSurveyAnsSeq())) {

                corporateSurveyAnsEntity.setCorporateSurveyAnsSeq(
                        sequenceGenerator.getNextSequence(PREFIX_CORPORATE_SURVEY_ANS.toString(),
                                TABLE_CORPORATE_SURVEY_ANS.toString()));

            } else {

                Optional<CorporateSurveyAnsEntity> corporateSurveyAnsEntityFind = corporateSurveyAnsRepository.findById(
                        corporateSurveyQusDTO.getCorporateSurveyAnsSeq());
                if (! corporateSurveyAnsEntityFind.isPresent()) {
                    throw new CustomException(ApiStatus.NOT_FOUND);
                }
                corporateSurveyAnsEntity = corporateSurveyAnsEntityFind.get();

            }

            corporateSurveyAnsEntity.setCorporateSurveyQusSeq(corporateSurveyQusDTO.getCorporateSurveyQusSeq());
            corporateSurveyAnsEntity.setInternCorporateSurveySeq(
                    internCorporateSurveyEntitySave.getInternCorporateSurveySeq());
            corporateSurveyAnsEntity.setScore(Integer.parseInt(corporateSurveyQusDTO.getScore()));
            corporateSurveyAnsEntity.setDelYn(Constants.STATE_N);

            listAnsEntity.add(corporateSurveyAnsEntity);

        }

        corporateSurveyAnsRepository.saveAll(listAnsEntity);

        return true;
    }

}
