package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.exceptions.DataBaseException;
import com.devsuperior.dscatalog.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.fixtures.CategoryFixture;
import com.devsuperior.dscatalog.fixtures.ProductFixture;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private ProductDTO productDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = ProductFixture.product();
        productDTO = ProductFixture.productDTO();
        page = new PageImpl<>(List.of(product));
        category = CategoryFixture.category();

        doNothing().when(repository).deleteById(existingId);
        doThrow(DataBaseException.class).when(repository).deleteById(dependentId);

        when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(repository.getReferenceById(nonExistingId)).thenReturn(null);

        when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        when(categoryRepository.getReferenceById(nonExistingId)).thenReturn(null);
    }

    @Test
    public void updateShouldReturnUpdatedProductWhenExistingId() {
        Product result = service.update(existingId, productDTO);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, times(1)).save(any());
    }

    @Test
    public void findByIdShouldReturnProductWhenExistingId() {
        Product result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));

        Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, times(1)).findAll(pageable);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        when(repository.existsById(nonExistingId)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).existsById(nonExistingId);
        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        when(repository.existsById(existingId)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(existingId));

        Mockito.verify(repository, Mockito.never()).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowDataBaseExceptionWhenDependentId() {
        when(repository.existsById(dependentId)).thenReturn(true);

        Assertions.assertThrows(DataBaseException.class, () -> service.delete(dependentId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }
}
