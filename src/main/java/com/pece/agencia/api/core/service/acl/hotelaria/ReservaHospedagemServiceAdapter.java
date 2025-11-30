package com.pece.agencia.api.core.service.acl.hotelaria;

import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.core.domain.OfertaHospedagem;
import com.pece.agencia.api.core.domain.Periodo;
import com.pece.agencia.api.core.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.service.acl.hotelaria.mapper.ReservaHospedagemRequestMapper;
import com.pece.agencia.api.hotelaria.PlataformaHospedagem;
import com.pece.agencia.api.hotelaria.PlataformaHospedagemOfflineException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ReservaHospedagemServiceAdapter {

    private final ReservaHospedagemRequestMapper mapper;
    private final PlataformaHospedagem hospedagemService;

    public String reservar(OfertaHospedagem hospedagem, Cliente cliente, Periodo periodoViagem) throws PlataformaParceiroOfflineException {
        try {
            PlataformaHospedagem.ReservaHospedagemRequest request = mapper.toRequest(hospedagem, cliente, periodoViagem);
            return hospedagemService.reservar(request);
        } catch (PlataformaHospedagemOfflineException ex) {
            throw new PlataformaParceiroOfflineException(format("plataforma-hospedagem-%s", ex.getPlataforma()), ex);
        }
    }

    public String obterIdPlataforma(OfertaHospedagem hospedagem) {
        return hospedagemService.obterIdPlataforma(hospedagem.getId());
    }
}
