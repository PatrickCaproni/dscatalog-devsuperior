package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.exceptions.EntityNotFoundException;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

}
