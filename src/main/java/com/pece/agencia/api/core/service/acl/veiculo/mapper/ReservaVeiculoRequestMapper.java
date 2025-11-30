package com.pece.agencia.api.core.service.acl.veiculo.mapper;

import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.core.domain.Localidade;
import com.pece.agencia.api.core.domain.OfertaLocacaoVeiculo;
import com.pece.agencia.api.core.domain.Periodo;
import com.pece.agencia.api.veiculo.internal.service.LocacaoVeiculoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        MotoristaMapper.class,
        VeiculoACLPeriodoMapper.class
    }
)
public interface ReservaVeiculoRequestMapper {
    @Mapping(source = "destino.id", target = "codigoLocalidade")
    @Mapping(source = "ofertaLocacaoVeiculo.categoriaVeiculo", target = "categoria")
    @Mapping(source = "cliente", target = "motorista")
    @Mapping(source = "periodoLocacaao", target = "periodo")
    LocacaoVeiculoService.ReservaVeiculoRequest toRequest(OfertaLocacaoVeiculo ofertaLocacaoVeiculo, Localidade destino, Cliente cliente, Periodo periodoLocacaao);
}
