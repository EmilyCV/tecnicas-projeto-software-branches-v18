package com.pece.agencia.api.core.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ClienteNaoEncontradoException extends  Exception {
    private final UUID clientId;

    public ClienteNaoEncontradoException(UUID clientId) {
        super(String.format("Pacote com ID %s nao encontrado", clientId));
        this.clientId = clientId;
    }
}
