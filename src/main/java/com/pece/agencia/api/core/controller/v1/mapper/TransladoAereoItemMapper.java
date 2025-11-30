package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.OfertaTransladoAereo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface TransladoAereoItemMapper {
    @Mapping(source = "parceiroResponsavel.nome", target = "nomeCompanhia")
    @Mapping(source = "parceiroResponsavel.emailContato.endereco", target = "email")
    @Mapping(source = "parceiroResponsavel.telefoneContato", target = "telefone")
    @Mapping(source = "vooIda.numero", target = "vooIdaNumero")
    @Mapping(source = "vooIda.horario", target = "vooIdaHora")
    @Mapping(source = "vooVolta.numero", target = "vooVoltaNumero")
    @Mapping(source = "vooVolta.horario", target = "vooVoltaHora")
    com.pece.agencia.api.core.controller.v1.dto.TransladoAereoItem toDTO(OfertaTransladoAereo entity);

}
