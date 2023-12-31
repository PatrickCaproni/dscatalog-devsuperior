package com.devsuperior.dscatalog.dtos;

import com.devsuperior.dscatalog.entities.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ProductDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Double price;
    private Instant date;
    private String description;
    private String imgUrl;
    private List<CategoryDTO> categories = new ArrayList<>();
}
