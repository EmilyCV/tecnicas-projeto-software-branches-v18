package com.pece.agencia.api.core.service.acl.veiculo.mapper;

import com.pece.agencia.api.veiculo.Periodo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface VeiculoACLPeriodoMapper {
    @Mapping(source = "inicio", target = "inicio")
    @Mapping(source = "fim", target = "fim")
    Periodo toPeriodo(com.pece.agencia.api.core.domain.Periodo periodo);
}
