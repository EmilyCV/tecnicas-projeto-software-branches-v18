package com.pece.agencia.api.core.service.acl.hotelaria.mapper;

import com.pece.agencia.api.hotelaria.Periodo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface PeriodoMapper {
    @Mapping(source = "inicio", target = "inicio")
    @Mapping(source = "fim", target = "fim")
    Periodo toPeriodo(com.pece.agencia.api.core.domain.Periodo periodo);
}
