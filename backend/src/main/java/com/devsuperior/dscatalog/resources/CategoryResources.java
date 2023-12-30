package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.mappers.CategoryMapper;
import com.devsuperior.dscatalog.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/categories")
public class CategoryResources implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        // log.info("[Categories Controller] - List all categories");
        List<Category> list = service.findAll();
        return ResponseEntity.ok().body(mapper.toDto(list));
    }
}
