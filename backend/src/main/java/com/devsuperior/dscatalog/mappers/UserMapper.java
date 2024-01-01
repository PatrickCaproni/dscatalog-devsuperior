package com.devsuperior.dscatalog.mappers;

import com.devsuperior.dscatalog.dtos.UserDTO;
import com.devsuperior.dscatalog.dtos.UserInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.mappers.map.struct.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper implements EntityMapper<UserDTO, User> {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Mapping(target = "password", source = "password", qualifiedByName = "encrypt")
    public abstract User toEntityWithPassword(UserInsertDTO dto);

    @Named("encrypt")
    protected String passwordEncrypt(String password) {
        return passwordEncoder.encode(password);
    }
}