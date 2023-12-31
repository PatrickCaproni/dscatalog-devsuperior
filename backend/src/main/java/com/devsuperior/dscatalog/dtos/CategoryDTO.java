package com.devsuperior.dscatalog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long Id;
    private String name;
}
