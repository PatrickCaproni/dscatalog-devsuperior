package com.devsuperior.dscatalog.mappers;

import com.devsuperior.dscatalog.dtos.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.utils.map.struct.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    default Page<ProductDTO> toDtoPage(Page<Product> products) {
        List<ProductDTO> productDTOList = products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOList, products.getPageable(), products.getTotalElements());
    }

}