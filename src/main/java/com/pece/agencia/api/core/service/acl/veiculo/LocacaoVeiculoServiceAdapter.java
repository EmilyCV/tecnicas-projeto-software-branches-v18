package com.pece.agencia.api.core.service.acl.veiculo;

import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.core.domain.Localidade;
import com.pece.agencia.api.core.domain.OfertaLocacaoVeiculo;
import com.pece.agencia.api.core.domain.Periodo;
import com.pece.agencia.api.core.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.service.acl.veiculo.mapper.ReservaVeiculoRequestMapper;
import com.pece.agencia.api.veiculo.PlataformaLocacaoVeiculo;
import com.pece.agencia.api.veiculo.PlataformaLocacaoVeiculoOfflineException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocacaoVeiculoServiceAdapter {

    private final ReservaVeiculoRequestMapper mapper;
    private final PlataformaLocacaoVeiculo service;

    public String locar(OfertaLocacaoVeiculo ofertaLocacaoVeiculo, Cliente motorista, Localidade destino, Periodo periodoViagem) throws PlataformaParceiroOfflineException {
        try {
            PlataformaLocacaoVeiculo.ReservaVeiculoRequest request = mapper.toRequest(ofertaLocacaoVeiculo, destino, motorista, periodoViagem);
            return service.locar(request);
        } catch (PlataformaLocacaoVeiculoOfflineException ex) {
            throw new PlataformaParceiroOfflineException("locacao-veiculo", ex);
        }
    }

    public String obterCodigoPlataformaLocacaoVeiculo(Localidade destino) {
       return obterCodigoPlataformaLocacaoVeiculo(destino.getId());
    }

    public String obterCodigoPlataformaLocacaoVeiculo(UUID destino) {
        return service.obterCodigoPlataformaLocacaoVeiculo(destino);
    }
}
