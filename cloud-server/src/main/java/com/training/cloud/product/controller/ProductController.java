package com.training.cloud.product.controller;

import java.math.BigDecimal;
import java.util.List;
import com.training.cloud.product.data.ProductFilterData;
import com.training.cloud.product.entity.Product;
import com.training.cloud.product.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> findAllProducts(@RequestParam(required = false) final BigDecimal priceFrom,
                                                        @RequestParam(required = false) final BigDecimal priceTo,
                                                        @RequestParam(required = false) final String name,
                                                        @RequestParam(required = false) final String sort) {
        final ProductFilterData productFilterData = createProductFilterData(name, priceFrom, priceTo, sort);
        return new ResponseEntity<>(productService.findAllProducts(productFilterData), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String code) {
        productService.delete(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable String code, @RequestBody Product productDetails) {
        return new ResponseEntity<>(productService.updateProduct(code, productDetails), HttpStatus.OK);
    }

    private ProductFilterData createProductFilterData(final String name, final BigDecimal priceFrom, final BigDecimal priceTo, final String sort) {
        ProductFilterData productFilterData = new ProductFilterData();
        productFilterData.setName(name);
        productFilterData.setPriceFrom(priceFrom);
        productFilterData.setPriceTo(priceTo);
        productFilterData.setSort(sort);
        return productFilterData;
    }
}