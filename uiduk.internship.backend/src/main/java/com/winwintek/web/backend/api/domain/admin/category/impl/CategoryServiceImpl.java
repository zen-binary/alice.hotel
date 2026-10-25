package com.winwintek.web.backend.api.domain.admin.category.impl;


import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.domain.admin.category.service.CategoryService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.category.CategoryCreResDTO;
import com.winwintek.web.backend.api.dto.admin.category.CategoryPageDto;
import com.winwintek.web.backend.api.dto.admin.category.CategoryResponseDTO;
import com.winwintek.web.backend.api.entity.admin.category.CategoryEntity;
import com.winwintek.web.backend.api.repository.admin.category.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_CATEGORY;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_CATEGORY;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private CommonService commonService;

    /**
     * Our Story 조회
     *
     * @param id
     * @return
     */
//    public CategoryResponseDTO getById(String id) {
//        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
//        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow();
//
//        categoryResponseDTO = modelMapper.map(categoryEntity, CategoryResponseDTO.class);
//
//        return categoryResponseDTO;
//    }

    /**
     * Our Story 등록
     */
    @Override
    public CategoryResponseDTO createCategory (CategoryCreResDTO category) {
        CategoryEntity categoryEntity = modelMapper.map(category, CategoryEntity.class);
        categoryEntity.setId(sequenceGenerator.getNextSequence(PREFIX_CATEGORY.toString(), TABLE_CATEGORY.toString()));
        return modelMapper.map(categoryRepository.save(categoryEntity), CategoryResponseDTO.class);
    }

    /**
     * Our Story 수정
     *
     * @param storyDTO
     * @throws IOException
     */
//    public void updateStory(StoryDTO storyDTO) {
//        Optional<StoryEntity> story = storyRepository.findById(storyDTO.getId());
//        if (story.isPresent()) {
//            StoryEntity storyEntity = story.get();
//
//            storyEntity.setName(storyDTO.getName());
//            storyEntity.setSubTitle(storyDTO.getSubTitle());
//            storyEntity.setTitle(storyDTO.getTitle());
//            storyEntity.setDepartment(storyDTO.getDepartment());
//            storyEntity.setContent(storyDTO.getContent());
//            storyEntity.setDisplay(storyDTO.getDisplay());
//            storyEntity.setVideoUrl(storyDTO.getVideoUrl());
//
//            storyRepository.save(storyEntity);
//        }
//    }

    /**
     * Our Story 삭제
     *
     * @param id
     */
//    public void deleteStory(Long id) {
//        Optional<StoryEntity> story = storyRepository.findById(id);
//        if (story.isPresent()) {
//            StoryEntity storyEntity = story.get();
//
//            storyEntity.setDeleteFlag(1);
//            storyRepository.save(storyEntity);
//        }
//    }

    /**
     * category 검색
     */
    @Override
    public Page<CategoryResponseDTO> getAllCategory (CategoryPageDto categoryPageDto) {
        int page = categoryPageDto.getPage();
        int size = categoryPageDto.getSize();
        String sort = categoryPageDto.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);
        Page<CategoryEntity> categories = categoryRepository.search(pageable,0);
        return categories.map(category -> modelMapper.map(category, CategoryResponseDTO.class));
    }

}
