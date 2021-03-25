package com.training.cloud.category.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.training.cloud.category.data.CategoryFilterData;
import com.training.cloud.category.entity.Category;
import com.training.cloud.category.repository.CategoryRepository;
import com.training.cloud.category.service.CategoryService;
import com.training.cloud.category.specification.CategorySpecification;
import com.training.cloud.application.util.SortUtil;
import com.training.cloud.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findByCode(String code) {
        return categoryRepository.findByCode(code);
    }

    @Override
    public List<Category> findAll(final CategoryFilterData categoryFilterData) {
        final Specification<Category> categorySpecification = CategorySpecification.containsName(categoryFilterData.getName());
        final Sort sort = SortUtil.createSort(categoryFilterData.getSort(), "name");
        return categoryRepository.findAll(categorySpecification, sort).stream()
                .distinct().collect(Collectors.toList());
    }

    @Override
    public List<Product> findAllProductsForCategory(String categoryCode) {
        final Category category = categoryRepository.findByCode(categoryCode);
        return new ArrayList<>(category.getProducts());
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(String code, Category categoryDetails) {
        final Category category = categoryRepository.findByCode(code);
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(String code) {
        final Category category = categoryRepository.findByCode(code);
        categoryRepository.delete(category);
    }
}
