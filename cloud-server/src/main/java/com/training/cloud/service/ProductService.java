package com.training.cloud.service;

import java.util.List;
import com.training.cloud.data.ProductFilterData;
import com.training.cloud.entity.Product;

public interface ProductService {

    Product findProductByCode(String code);

    List<Product> findAllProductsForCategory(String categoryCode);

    Product save(Product product);

    void delete(String code);

    Product updateProduct(String code, Product productDetails);

    List<Product> findAllProducts(ProductFilterData productFilterData);
}
