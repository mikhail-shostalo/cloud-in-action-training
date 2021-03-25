package com.training.cloud.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.training.cloud.product.data.ProductFilterData;
import com.training.cloud.category.entity.Category;
import com.training.cloud.product.entity.Product;
import com.training.cloud.category.repository.CategoryRepository;
import com.training.cloud.product.repository.ProductRepository;
import com.training.cloud.product.service.ProductService;
import com.training.cloud.product.specification.ProductSpecification;
import com.training.cloud.application.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product findProductByCode(String code) {
        return productRepository.findByCode(code);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(String code) {
        final Product product = productRepository.findByCode(code);
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(String code, Product productDetails) {
        final Product product = productRepository.findByCode(code);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts(ProductFilterData productFilterData) {
        final Specification<Product> productSpecification = createSpecification(productFilterData);
        final Sort sort = SortUtil.createSort(productFilterData.getSort(), "name");
        return productRepository.findAll(productSpecification, sort);
    }

    private Specification<Product> createSpecification(final ProductFilterData productFilterData) {
        return ProductSpecification.contain(productFilterData.getName())
                .and(ProductSpecification.betweenPrice(productFilterData.getPriceFrom(), productFilterData.getPriceTo()));
    }
}
