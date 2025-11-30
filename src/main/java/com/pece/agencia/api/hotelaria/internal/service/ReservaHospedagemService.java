package com.pece.agencia.api.hotelaria.internal.service;

import com.pece.agencia.api.hotelaria.PlataformaHospedagem;
import com.pece.agencia.api.hotelaria.PlataformaHospedagemOfflineException;
import com.pece.agencia.api.hotelaria.internal.domain.OfertaHospedagemPlataformaMapping;
import com.pece.agencia.api.hotelaria.internal.repository.OfertaHospedagemPlataformaMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservaHospedagemService implements PlataformaHospedagem {
    private final OfertaHospedagemPlataformaMappingRepository repository;

    private final List<ReservaHospedagemServiceHandler> handlers;

    public String reservar(ReservaHospedagemRequest request) throws PlataformaHospedagemOfflineException {
        OfertaHospedagemPlataformaMapping mapping = repository.findById(request.codigoPromocao()).get();
        for (ReservaHospedagemServiceHandler handler : handlers) {
            if (handler.accepts(mapping)) {
                return handler.reservar(mapping, request);
            }
        }
        throw new IllegalArgumentException("Nenhum handler encontrado para a plataforma: " + mapping.getPlataforma());
    }

    public String obterIdPlataforma(UUID codigoPromocao) {
        OfertaHospedagemPlataformaMapping mapping = repository.findById(codigoPromocao).get();
        return mapping.getCodigoHotel();
    }
}