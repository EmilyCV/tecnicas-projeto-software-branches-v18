package com.pece.agencia.api.core.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public record Endereco(String cep, @ManyToOne(fetch = FetchType.LAZY) Localidade localidade, String logradouro, String numero, String complemento, String bairro) {
    public Endereco {
        Objects.requireNonNull(cep);
        Objects.requireNonNull(localidade);
        Objects.requireNonNull(logradouro);
        Objects.requireNonNull(numero);
        Objects.requireNonNull(bairro);
    }
}
