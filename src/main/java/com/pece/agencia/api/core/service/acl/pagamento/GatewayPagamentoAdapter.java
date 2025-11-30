package com.pece.agencia.api.core.service.acl.pagamento;

import com.pece.agencia.api.core.domain.DadosCartao;
import com.pece.agencia.api.core.exception.PlataformaParceiroOfflineException;
import com.pece.agencia.api.core.service.acl.pagamento.mapper.DadosCartaoMapper;
import com.pece.agencia.api.pagamento.FalhaCobrancaException;
import com.pece.agencia.api.pagamento.FalhaConectividadeException;
import com.pece.agencia.api.pagamento.GatewayPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayPagamentoAdapter {
    private final GatewayPagamento service;
    private final DadosCartaoMapper mapper;

    public String pagar(DadosCartao dadosCartao, double value) throws com.pece.agencia.api.core.exception.FalhaCobrancaException, PlataformaParceiroOfflineException {
        try {
            return service.pagar(mapper.toDadosCartao(dadosCartao), value);
        } catch (FalhaCobrancaException ex) {
            throw new com.pece.agencia.api.core.exception.FalhaCobrancaException("Falha ao processar o pagamento", ex);
        } catch (FalhaConectividadeException ex) {
            throw new PlataformaParceiroOfflineException("stripe", ex);
        }
    }
}
