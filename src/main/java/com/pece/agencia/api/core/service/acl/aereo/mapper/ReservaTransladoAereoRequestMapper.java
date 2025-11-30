package com.pece.agencia.api.core.service.acl.aereo.mapper;

import com.pece.agencia.api.aereo.internal.service.ReservaTransladoAereoService;
import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.core.domain.DadosVoo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(
    componentModel = "spring",
    uses = {
            DadosVooMapper.class,
            PassageiroMapper.class
    }
)
public interface ReservaTransladoAereoRequestMapper {
    @Mapping(source = "cliente", target = "passageiro")
    @Mapping(source = "dadosVoo", target = "dadosVoo")
    @Mapping(source = "data", target = "data")
    ReservaTransladoAereoService.ReservaTransladoAereoRequest toRequest(Cliente cliente, DadosVoo dadosVoo, LocalDate data);
}
