package com.devsuperior.dscatalog.fixtures;

import com.devsuperior.dscatalog.entities.Category;

public class CategoryFixture {

    public static Category category() {
        Category entity = new Category();

        entity.setId(1L);
        entity.setName("Computer");

        return entity;
    }
}
