package com.pece.agencia.api.pagamento.internal.stripe.service;


import com.pece.agencia.api.common.archunit.MayParseExceptionMessage;
import com.pece.agencia.api.pagamento.DadosCartao;
import com.pece.agencia.api.pagamento.FalhaCobrancaException;
import com.pece.agencia.api.pagamento.FalhaConectividadeException;
import com.pece.agencia.api.pagamento.GatewayPagamento;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeGatewayPagamento implements GatewayPagamento {
    public String doPagar(DadosCartao dadosCartao, double value) throws StripeException {
        TokenCreateParams.Card card = TokenCreateParams.Card.builder()
                .setNumber(dadosCartao.numero())
                .setExpMonth(String.valueOf(dadosCartao.dataExpiracao().getMonthValue()))
                .setExpYear(String.valueOf(dadosCartao.dataExpiracao().getYear()))
                .setCvc(dadosCartao.cvc())
                .build();

        Token token = Token.create(TokenCreateParams.builder().setCard(card).build());

        ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                .setDescription("Venda de Pacote")
                .setCurrency("brl")
                .setAmount((long)(value * 100)) // em centavos
                .setSource(token.getCard().getId())
                .build();
        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }

    @Override
    @MayParseExceptionMessage
    public String pagar(DadosCartao dadosCartao, double value) throws FalhaCobrancaException, FalhaConectividadeException {
        try {
            return doPagar(dadosCartao, value);
        } catch (StripeException ex) {
            if (ex.getMessage().contains("card_declined")) {
                throw new FalhaCobrancaException("Pagamento recusado pelo emissor do cartão", ex);
            } else if (ex.getMessage().contains("IOException during API request to Stripe")) {
                throw new FalhaConectividadeException("Falha de conectividade com o gateway de pagamento", ex);
            } else {
                throw new RuntimeException("Falha ao processar pagamento com Stripe", ex);
            }
        }
    }
}
