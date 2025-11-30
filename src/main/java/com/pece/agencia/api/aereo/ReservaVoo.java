package com.pece.agencia.api.aereo;

import java.time.LocalDateTime;

public record ReservaVoo(String eTicket, String assento, LocalDateTime horarioEmbarque, DadosVoo dadosVoo) {
}
