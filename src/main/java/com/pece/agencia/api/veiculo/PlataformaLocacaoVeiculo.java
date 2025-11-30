package com.pece.agencia.api.veiculo;


import java.util.UUID;

public interface PlataformaLocacaoVeiculo {
    String locar(ReservaVeiculoRequest request) throws PlataformaLocacaoVeiculoOfflineException;
    String obterCodigoPlataformaLocacaoVeiculo(UUID localidade);

    record ReservaVeiculoRequest(UUID codigoLocalidade, String categoria, Motorista motorista, Periodo periodo) {
    }
}
