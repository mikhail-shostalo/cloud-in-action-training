package com.training.cloud.category.service;

import java.util.List;
import com.training.cloud.category.data.CategoryFilterData;
import com.training.cloud.category.entity.Category;
import com.training.cloud.product.entity.Product;

public interface CategoryService {

    Category findByCode(String code);

    List<Category> findAll(CategoryFilterData categoryFilterData);

    List<Product> findAllProductsForCategory(String categoryCode);

    Category save(Category category);

    Category update(String code, Category categoryDetails);

    void delete(String code);
}
