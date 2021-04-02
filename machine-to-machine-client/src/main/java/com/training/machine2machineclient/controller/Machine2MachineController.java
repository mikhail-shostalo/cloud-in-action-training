package com.training.machine2machineclient.controller;

import java.util.List;
import com.training.machine2machineclient.dto.CategoryDto;
import com.training.machine2machineclient.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping("/machine-to-machine")
public class Machine2MachineController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> findProducts(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        List<ProductDto> productDtoList = createResponseSpec("/products", authorizedClient)
                .bodyToFlux(ProductDto.class)
                .collectList().block();
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> findCategories(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        List<CategoryDto> categoryDtoList = createResponseSpec("/categories", authorizedClient)
                .bodyToFlux(CategoryDto.class)
                .collectList().block();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }

    private WebClient.ResponseSpec createResponseSpec(final String url, OAuth2AuthorizedClient authorizedClient) {
        return webClient.get().uri(url)
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve();
    }
}