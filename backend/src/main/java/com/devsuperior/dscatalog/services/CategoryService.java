package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dtos.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.exceptions.DataBaseException;
import com.devsuperior.dscatalog.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;

@Service
public class CategoryService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<Category> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional
    public Category insert(Category category) {
        return repository.save(category);
    }

    @Transactional
    public Category update(Long id, CategoryDTO categoryDTO) {
        try {
            Category categoryToBeUpdated = repository.getReferenceById(id);
            categoryToBeUpdated.setName(categoryDTO.getName());
            categoryToBeUpdated = repository.save(categoryToBeUpdated);
            return categoryToBeUpdated;
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            try {
                repository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new DataBaseException("Integrity violation");
            }
        } else {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

}
