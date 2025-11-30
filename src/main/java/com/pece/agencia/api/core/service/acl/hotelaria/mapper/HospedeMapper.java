package com.pece.agencia.api.core.service.acl.hotelaria.mapper;

import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.hotelaria.Hospede;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface HospedeMapper {
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "email.endereco", target = "email")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    Hospede toHospede(Cliente cliente);
}