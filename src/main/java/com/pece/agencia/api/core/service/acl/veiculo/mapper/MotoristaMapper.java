package com.pece.agencia.api.core.service.acl.veiculo.mapper;

import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.veiculo.Motorista;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface MotoristaMapper {
    @Mapping(source = "nome", target = "nome")
    Motorista toMotorista(Cliente cliente);
}
