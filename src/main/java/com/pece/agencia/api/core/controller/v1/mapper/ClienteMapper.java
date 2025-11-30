package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.Cliente;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = EnderecoMapper.class
)
public interface ClienteMapper {
    @Mapping(source = "email.endereco", target = "email")
    com.pece.agencia.api.core.controller.v1.dto.Cliente toDTO(Cliente entity);

    @AfterMapping
    default void processEnderecoId(@MappingTarget com.pece.agencia.api.core.controller.v1.dto.Cliente dto) {
        dto.getEndereco().setId(dto.getId());
    }
}
