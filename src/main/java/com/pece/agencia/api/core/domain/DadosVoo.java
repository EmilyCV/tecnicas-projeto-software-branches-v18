package com.pece.agencia.api.core.domain;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public record DadosVoo(
        String numero,
        LocalTime horario
) {
    public String getNumero() {
        return numero();
    }

    public LocalTime getHorario() {
        return horario();
    }
}
