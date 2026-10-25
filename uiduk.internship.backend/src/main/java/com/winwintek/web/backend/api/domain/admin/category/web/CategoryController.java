package com.winwintek.web.backend.api.domain.admin.category.web;


import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.admin.category.service.CategoryService;
import com.winwintek.web.backend.api.dto.admin.category.CategoryCreResDTO;
import com.winwintek.web.backend.api.dto.admin.category.CategoryPageDto;
import com.winwintek.web.backend.api.dto.admin.category.CategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping ("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Our Story 조회
     *
     * @param id
     * @return
     */
//    @PostMapping("/detail")
//    public ApiResponse<CategoryResponseDTO> getStoryById(CategoryIdResDTO categoryIdResDTO) {
//        try {
//            CategoryResponseDTO categoryResponseDTO = categoryServiceImpl.getById(categoryIdResDTO.getId());
//            return new
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    /**
     * Our Story 등록
     */
    @PostMapping (value = "/")
    public ApiResponse<CategoryResponseDTO> createCategory (@RequestBody CategoryCreResDTO category) {
        CategoryResponseDTO createdCategory = categoryService.createCategory(category);
        return new ApiResponse<>(ApiStatus.CREATED, createdCategory);
    }

    /**
     * Our Story 수정
     *
     * @param storyDTO
     * @throws
     */
//    @PutMapping(value = "/")
//    public void updateStory(@RequestBody StoryDTO storyDTO) {
//        try {
//            storyService.updateStory(storyDTO);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    /**
     * Our Story 삭제
     *
     * @param id
     */
//    @DeleteMapping("/{id}")
//    public void deleteStory(@PathVariable("id") Long id) {
//        try {
//            storyService.deleteStory(id);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    /**
     * Our Story 검색
     *
     * @return
     */
    @PostMapping ("/list")
    public ApiResponse<Page<CategoryResponseDTO>> getAllCategories (@RequestBody CategoryPageDto categoryPageDto) {
        Page<CategoryResponseDTO> categories = categoryService.getAllCategory(categoryPageDto);
        return new ApiResponse<>(ApiStatus.SUCCESS, categories);
    }

}
