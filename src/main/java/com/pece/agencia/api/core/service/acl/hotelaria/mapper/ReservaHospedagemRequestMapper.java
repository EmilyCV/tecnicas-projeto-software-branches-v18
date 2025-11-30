package com.pece.agencia.api.core.service.acl.hotelaria.mapper;

import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.core.domain.OfertaHospedagem;
import com.pece.agencia.api.core.domain.Periodo;
import com.pece.agencia.api.hotelaria.PlataformaHospedagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        HospedeMapper.class,
        PeriodoMapper.class
    }
)
public interface ReservaHospedagemRequestMapper {
    @Mapping(source = "hospedagem.id", target = "codigoPromocao")
    @Mapping(source = "cliente", target = "hospede")
    @Mapping(source = "periodoHospedagem", target = "periodo")
    PlataformaHospedagem.ReservaHospedagemRequest toRequest(OfertaHospedagem hospedagem, Cliente cliente, Periodo periodoHospedagem);
}
