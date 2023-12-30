package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.mappers.CategoryMapper;
import com.devsuperior.dscatalog.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;

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
    public ResponseEntity<Page<CategoryDTO>> findAllPaged(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                          @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                          @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
        log.info("[Categories Controller] - List all categories paged");
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Category> categories = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(categories.map(category -> mapper.toDto(category)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        log.info("[Categories Controller] - Get category by id");
        Category category = service.findById(id);
        return ResponseEntity.ok().body(mapper.toDto(category));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO category) {
        log.info("[Categories Controller] - Register new Category");
        Category newCategory = service.insert(mapper.toEntity(category));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(newCategory));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO category) {
        log.info("[Categories Controller] - Update category by id");
        Category newCategory = service.update(id, category);
        return ResponseEntity.ok().body(mapper.toDto(newCategory));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("[Categories Controller] - Delete category by id");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
