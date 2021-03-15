package com.training.cloud.service;

import java.util.List;
import com.training.cloud.data.CategoryFilterData;
import com.training.cloud.entity.Category;

public interface CategoryService {

    Category findByCode(String code);

    List<Category> findAll(CategoryFilterData categoryFilterData);

    Category save(Category category);

    Category update(String code, Category categoryDetails);

    void delete(String code);
}
