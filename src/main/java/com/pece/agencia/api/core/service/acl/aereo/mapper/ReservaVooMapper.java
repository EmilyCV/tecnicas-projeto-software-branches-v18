package com.pece.agencia.api.core.service.acl.aereo.mapper;

import com.pece.agencia.api.core.domain.ReservaVoo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
            DadosVooMapper.class,
        }
)
public interface ReservaVooMapper {
    @Mapping(source = "eTicket", target = "ETicket")
    @Mapping(source = "assento", target = "assento")
    @Mapping(source = "horarioEmbarque", target = "horarioEmbarque")
    @Mapping(source = "dadosVoo", target = "dadosVoo")
    ReservaVoo toReservaVoo(com.pece.agencia.api.aereo.ReservaVoo reservaVoo);
}
