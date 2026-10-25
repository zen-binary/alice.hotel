package com.winwintek.web.backend.api.repository.admin.category;

import com.winwintek.web.backend.api.entity.admin.category.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    Page<CategoryEntity> findAll(Pageable pageable);

    @Query(value = """
        SELECT * 
        FROM category 
        WHERE DELETE_FLAG = :delete
    """, nativeQuery = true)
    Page<CategoryEntity> search(Pageable pageable, @Param("delete") int delete);
}
