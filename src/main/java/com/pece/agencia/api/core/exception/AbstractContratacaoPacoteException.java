package com.pece.agencia.api.core.exception;

import com.pece.agencia.api.core.domain.Pacote;
import lombok.Getter;

@Getter
public abstract class AbstractContratacaoPacoteException extends Exception {
    private final Pacote pacote;
    public AbstractContratacaoPacoteException(String message, Pacote pacote) {
        super(message);
        this.pacote = pacote;
    }
}
