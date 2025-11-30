package com.pece.agencia.api.pagamento;


public interface GatewayPagamento {
    String pagar(DadosCartao dadosCartao, double value) throws FalhaCobrancaException, FalhaConectividadeException;
}
