package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.entities.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        log.info("[Categories Controller] - List all categories");
        List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "Books"));
        list.add(new Category(2L, "Electronics"));
        return ResponseEntity.ok().body(list);
    }
}
