package com.devsuperior.dscatalog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "fist name is a required field")
    private String firstName;

    @NotEmpty(message = "last name is a required field")
    private String lastName;

    @Email(message = "email must be valid")
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();

}
