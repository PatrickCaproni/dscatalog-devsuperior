package com.devsuperior.dscatalog.fixtures;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductFixture {

    public static Product product() {
        Product entity = new Product();

        entity.setId(1L);
        entity.setName("Lord of the rings");
        entity.setDate(Instant.now());
        entity.setPrice(2500.00);
        entity.setDescription("just a game, just a game, just a game, just a game, just a game, just a game");
        entity.setImgUrl("url.game");
        entity.getCategories().add(CategoryFixture.category());

        return entity;
    }

    public static Product productWithoutId() {
        Product entity = new Product();

        entity.setName("Phone");
        entity.setDate(Instant.now());
        entity.setPrice(2500.00);
        entity.setDescription("just a phone, just a phone, just a phone, just a phone, just a phone, just a phone");
        entity.setImgUrl("url.phone");
        entity.getCategories().add(CategoryFixture.category());

        return entity;
    }

    public static ProductDTO productDTO() {
        Product product = product();
        return new ProductDTO(product, product.getCategories());
    }

    public static List<Product> productList() {
        List<Product> productList = new ArrayList<>();
        Product product = product();
        productList.add(product);
        return productList;
    }

    public static List<ProductDTO> productDTOList() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        Product product = product();
        productDTOList.add(new ProductDTO(product, product.getCategories()));
        return productDTOList;
    }
}
