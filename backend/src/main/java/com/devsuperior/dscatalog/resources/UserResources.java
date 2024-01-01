package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dtos.UserDTO;
import com.devsuperior.dscatalog.dtos.UserInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.mappers.UserMapper;
import com.devsuperior.dscatalog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserResources implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable) {
        log.info("[Users Controller] - List all users paged");
        Page<User> userPage = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(userPage.map(user -> mapper.toDto(user)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        log.info("[Users Controller] - Get user by id");
        User user = service.findById(id);
        return ResponseEntity.ok().body(mapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Validated @RequestBody UserInsertDTO user) {
        log.info("[Users Controller] - Register new User");
        User newUser = service.insert(mapper.toEntityWithPassword(user));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(newUser));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO user) {
        log.info("[Users Controller] - Update user by id");
        User newUser = service.update(id, user);
        return ResponseEntity.ok().body(mapper.toDto(newUser));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("[Users Controller] - Delete user by id");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
