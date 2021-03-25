package com.training.cloud.product.service;

import java.util.List;
import com.training.cloud.product.data.ProductFilterData;
import com.training.cloud.product.entity.Product;

public interface ProductService {

    Product findProductByCode(String code);

    Product save(Product product);

    void delete(String code);

    Product updateProduct(String code, Product productDetails);

    List<Product> findAllProducts(ProductFilterData productFilterData);
}
