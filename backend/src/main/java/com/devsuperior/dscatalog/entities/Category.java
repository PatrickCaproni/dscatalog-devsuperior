package com.devsuperior.dscatalog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
@Slf4j
@Table(name = "CATEGORY")
public class Category {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;
}
