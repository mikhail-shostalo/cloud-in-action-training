package com.training.cloud.controller;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.transaction.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.cloud.product.entity.Product;
import com.training.cloud.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static com.training.cloud.TestUtil.asJsonString;
import static com.training.cloud.TestUtil.easyRandomInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class ProductControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

//    @Test
//    public void shouldCreateNewProduct() throws Exception {
//        final Product product = easyRandomInstance().nextObject(Product.class);
//
//        mockMvc.perform(post("/products")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(product)))
//                .andExpect(status().isCreated());
//
//        assertThat(productService.findProductByCode(product.getCode())).isNotNull();
//    }

//    @Test
//    public void shouldUpdateExistedProduct() throws Exception {
//        final Product product = easyRandomInstance().nextObject(Product.class);
//        productService.save(product);
//
//        product.setPrice(BigDecimal.TEN);
//
//        mockMvc.perform(put("/products/" + product.getCode())
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(product)))
//                .andExpect(status().isOk());
//
//        assertThat(productService.findProductByCode(product.getCode()).getPrice()).isEqualTo(BigDecimal.TEN);
//    }

//    @Test
//    public void shouldDeleteExistedProduct() throws Exception {
//        final Product product = easyRandomInstance().nextObject(Product.class);
//        productService.save(product);
//
//        mockMvc.perform(delete("/products/" + product.getCode()))
//                .andExpect(status().isOk());
//
//        assertThat(productService.findProductByCode(product.getCode())).isNull();
//    }

    @Test
    public void shouldFindAllProducts() throws Exception {
        List<Product> products = randomizeProductList();
        products.forEach(product -> productService.save(product));

        MockHttpServletResponse response = mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result)
                .map(Product::getCode)
                .containsAll(products.stream().map(Product::getCode).collect(Collectors.toList()));
    }

    @Test
    public void shouldFilterProductsByPrice_WhenHighAndLowPricePresent() throws Exception {
        final BigDecimal priceFrom = BigDecimal.ONE;
        final BigDecimal priceTo = BigDecimal.valueOf(5.);
        updateProductPrices(randomizeProductList());

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("priceFrom", priceFrom.toString())
                                                                   .param("priceTo", priceTo.toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Product::getPrice)
                .allMatch(price -> price.intValue() >= priceFrom.intValue() && price.intValue() <= priceTo.intValue());
    }

    @Test
    public void shouldFilterProductsByPrice_WhenHighPriceIsNotPresent() throws Exception {
        final BigDecimal priceTo = BigDecimal.valueOf(5.);
        updateProductPrices(randomizeProductList());

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("priceTo", priceTo.toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Product::getPrice)
                .allMatch(price -> price.intValue() <= priceTo.intValue());
    }

    @Test
    public void shouldFilterProductsByPrice_WhenLowPriceIsNotPresent() throws Exception {
        final BigDecimal priceFrom = BigDecimal.valueOf(3.);
        updateProductPrices(randomizeProductList());

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("priceFrom", priceFrom.toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Product::getPrice)
                .allMatch(price -> price.intValue() >= priceFrom.intValue());
    }

    @Test
    public void shouldFilterProductsByName() throws Exception {
        final String name = "Test product";
        List<Product> products = randomizeProductList();
        products.forEach(product -> productService.save(product));
        IntStream.range(0, 3).forEach(index -> {
            Product product = products.get(index);
            product.setName(name + index);
            productService.updateProduct(product.getCode(), product);
        });

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("name", name))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Product::getName)
                .allMatch(productName -> productName.contains(name));
    }

    @Test
    public void shouldSortProductsByNameAsc() throws Exception {
        randomizeProductList()
                .forEach(product -> productService.save(product));

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Product::getName).isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    public void shouldSortProductsByNameDesc() throws Exception {
        randomizeProductList()
                .forEach(product -> productService.save(product));

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("sort", "name,desc"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Product::getName).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    public void shouldSortProductByPriceAsc() throws Exception {
        updateProductPrices(randomizeProductList());

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("sort", "price,asc"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).isSortedAccordingTo(Comparator.comparing(Product::getPrice));
    }

    @Test
    public void shouldSortProductByPriceDesc() throws Exception {
        updateProductPrices(randomizeProductList());

        MockHttpServletResponse response = mockMvc.perform(get("/products")
                                                                   .param("sort", "price,desc"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).isSortedAccordingTo(Comparator.comparing(Product::getPrice).reversed());
    }

    private void updateProductPrices(final List<Product> products) {
        IntStream.range(0, 9).forEach(index -> {
            Product product = products.get(index);
            product.setPrice(BigDecimal.valueOf(index));
            productService.save(product);
        });
    }

    private List<Product> randomizeProductList() {
        return easyRandomInstance().objects(Product.class, 10).collect(Collectors.toList());
    }

}
