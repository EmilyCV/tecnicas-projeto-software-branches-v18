package com.pece.agencia.api.core.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.time.LocalDateTime;

@Embeddable
public record ReservaVoo(
        String eticket,
        String assento,
        LocalDateTime horarioEmbarque,
        @Embedded
        DadosVoo dadosVoo
) {
    public String getETicket() {
        return eticket();
    }

    public String getAssento() {
        return assento();
    }

    public LocalDateTime getHorarioEmbarque() {
        return horarioEmbarque();
    }

    public DadosVoo getDadosVoo() {
        return dadosVoo();
    }
}
