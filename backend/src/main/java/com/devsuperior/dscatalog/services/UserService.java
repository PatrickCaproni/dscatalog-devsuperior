package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dtos.RoleDTO;
import com.devsuperior.dscatalog.dtos.UserDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.exceptions.DataBaseException;
import com.devsuperior.dscatalog.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Service
public class UserService implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<User> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional
    public User insert(User product) {
        return repository.save(product);
    }

    @Transactional
    public User update(Long id, UserDTO productDTO) {
        User productToBeUpdated = findById(id);
        copyDtoToEntity(productDTO, productToBeUpdated);
        productToBeUpdated = repository.save(productToBeUpdated);
        return productToBeUpdated;
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();

        for (RoleDTO roleDTO : dto.getRoles()) {
            Optional<Role> role = roleRepository.findById(roleDTO.getId());
            role.ifPresent(value -> entity.getRoles().add(value));
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
