package com.training.cloud.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.cloud.category.entity.Category;
import com.training.cloud.category.service.CategoryService;
import com.training.cloud.context.annatation.WithScope;
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
public class CategoryControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Test
    @WithScope("SCOPE_User.Write")
    public void shouldCreateNewCategory() throws Exception {
        final Category category = easyRandomInstance().nextObject(Category.class);

        mockMvc.perform(post("/categories")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(category)))
                .andExpect(status().isCreated());

        assertThat(categoryService.findByCode(category.getCode())).isNotNull();
    }

    @Test
    @WithScope("SCOPE_User.Write")
    public void shouldUpdateExistedCategory() throws Exception {
        final String name = "Test name";
        final Category category = easyRandomInstance().nextObject(Category.class);
        categoryService.save(category);
        category.setName(name);

        mockMvc.perform(put("/categories/" + category.getCode())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(category)))
                .andExpect(status().isOk());

        assertThat(categoryService.findByCode(category.getCode()).getName()).isEqualTo(name);
    }

    @Test
    public void shouldReturnProductsForCategory() throws Exception {
        final Product product = productService.save(easyRandomInstance().nextObject(Product.class));
        final Category category = easyRandomInstance().nextObject(Category.class);
        category.addProduct(product);
        categoryService.save(category);

        MockHttpServletResponse response = mockMvc.perform(get("/categories/" + category.getCode() + "/products"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Product::getCode)
                .contains(product.getCode());
    }

    @Test
    @WithScope("SCOPE_User.Write")
    public void shouldDeleteExistedCategory() throws Exception {
        final Category category = easyRandomInstance().nextObject(Category.class);
        categoryService.save(category);

        mockMvc.perform(delete("/categories/" + category.getCode()))
                .andExpect(status().isOk());

        assertThat(categoryService.findByCode(category.getCode())).isNull();
    }

    @Test
    public void shouldFindAllCategories() throws Exception {
        final List<Category> categories = randomizeCategories();
        categories.forEach(category -> categoryService.save(category));

        final MockHttpServletResponse response = mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Category> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Category::getCode)
                .containsAll(categories.stream().map(Category::getCode).collect(Collectors.toList()));
    }

    @Test
    public void shouldFindCategoriesByName() throws Exception {
        final String name = "Category1";
        final List<Category> categories = randomizeCategories();
        categories.forEach(category -> categoryService.save(category));
        final Category expectedCategory = categories.get(3);
        expectedCategory.setName(name + expectedCategory.getName());
        categoryService.update(expectedCategory.getCode(), expectedCategory);

        final MockHttpServletResponse response = mockMvc.perform(get("/categories")
                                                                         .param("name", name))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        final List<Category> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Category::getName)
                .allMatch(categoryName -> categoryName.contains(name));
    }

    @Test
    public void shouldSortByCategoryNameAsc() throws Exception {
        randomizeCategories().forEach(category -> categoryService.save(category));

        final MockHttpServletResponse response = mockMvc.perform(get("/categories")
                                                                         .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Category> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Category::getName).isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    public void shouldSortByCategoryNameDesc() throws Exception {
        randomizeCategories().forEach(category -> categoryService.save(category));

        final MockHttpServletResponse response = mockMvc.perform(get("/categories")
                                                                         .param("sort", "name,desc"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        final List<Category> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(result).map(Category::getName).isSortedAccordingTo(Comparator.reverseOrder());
    }

    private List<Category> randomizeCategories() {
        return easyRandomInstance().objects(Category.class, 10).collect(Collectors.toList());
    }
}
