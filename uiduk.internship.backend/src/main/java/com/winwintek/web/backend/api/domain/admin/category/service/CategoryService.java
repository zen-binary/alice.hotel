package com.winwintek.web.backend.api.domain.admin.category.service;


import com.winwintek.web.backend.api.dto.admin.category.CategoryCreResDTO;
import com.winwintek.web.backend.api.dto.admin.category.CategoryPageDto;
import com.winwintek.web.backend.api.dto.admin.category.CategoryResponseDTO;
import org.springframework.data.domain.Page;

public interface CategoryService {
    public Page<CategoryResponseDTO> getAllCategory (CategoryPageDto categoryPageDto);
    public CategoryResponseDTO createCategory(CategoryCreResDTO categoryCreResDTO);
}
