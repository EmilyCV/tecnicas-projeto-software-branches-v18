package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.Endereco;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = LocalidadeMapper.class
)
public interface EnderecoMapper {
    com.pece.agencia.api.core.controller.v1.dto.Endereco toDTO(Endereco entity);
}
