package com.pece.agencia.api.hotelaria.internal.service;

import com.pece.agencia.api.hotelaria.PlataformaHospedagemOfflineException;
import com.pece.agencia.api.hotelaria.internal.domain.OfertaHospedagemPlataformaMapping;

public interface ReservaHospedagemServiceHandler {
    boolean accepts(OfertaHospedagemPlataformaMapping mapping);
    String reservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request) throws PlataformaHospedagemOfflineException;
}
