package com.training.cloud.category.specification;

import com.training.cloud.category.entity.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    public static Specification<Category> containsName(final String name) {
        return ((root, query, criteriaBuilder) -> {
           if (StringUtils.isNotEmpty(name)) {
               return criteriaBuilder.like(root.get("name"), "%" + name + "%");
           }
           return null;
        });
    }
}
