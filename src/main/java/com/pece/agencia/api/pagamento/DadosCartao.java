package com.pece.agencia.api.pagamento;

import java.time.YearMonth;

public record DadosCartao(String numero, String cvc, YearMonth dataExpiracao) {
}
