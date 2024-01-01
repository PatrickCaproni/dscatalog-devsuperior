package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mappers.ProductMapper;
import com.devsuperior.dscatalog.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = "/products")
public class ProductResources implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAllPaged(Pageable pageable) {
        log.info("[Products Controller] - List all products paged");
        Page<Product> products = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(mapper.toDtoPage(products));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        log.info("[Products Controller] - Get product by id");
        Product product = service.findById(id);
        return ResponseEntity.ok().body(mapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO product) {
        log.info("[Products Controller] - Register new Product");
        Product newProduct = service.insert(mapper.toEntity(product));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(newProduct));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO product) {
        log.info("[Products Controller] - Update product by id");
        Product newProduct = service.update(id, product);
        return ResponseEntity.ok().body(mapper.toDto(newProduct));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("[Products Controller] - Delete product by id");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
