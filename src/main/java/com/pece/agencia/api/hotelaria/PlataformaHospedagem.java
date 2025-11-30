package com.pece.agencia.api.hotelaria;

import java.util.UUID;

public interface PlataformaHospedagem {
    String reservar(ReservaHospedagemRequest request) throws PlataformaHospedagemOfflineException;
    String obterIdPlataforma(UUID codigoPromocao);

    record ReservaHospedagemRequest(UUID codigoPromocao, Hospede hospede, Periodo periodo) {
    }
}
