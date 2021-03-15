package com.training.cloud.controller;

import java.util.List;
import com.training.cloud.data.CategoryFilterData;
import com.training.cloud.entity.Category;
import com.training.cloud.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> findAllCategories(@RequestParam(required = false) final String name,
                                                            @RequestParam(required = false) final String sort) {
        final CategoryFilterData categoryFilterData = createCategoryFilter(name, sort);
        return new ResponseEntity<>(categoryService.findAll(categoryFilterData), HttpStatus.OK);
    }

    private CategoryFilterData createCategoryFilter(final String name, final String sort) {
        final CategoryFilterData categoryFilterData = new CategoryFilterData();
        categoryFilterData.setName(name);
        categoryFilterData.setSort(sort);
        return categoryFilterData;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> updateCategory(@PathVariable String code, @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.update(code, category), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCategory(@PathVariable String code) {
        categoryService.delete(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}