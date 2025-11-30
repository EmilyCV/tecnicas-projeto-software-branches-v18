package com.pece.agencia.api.core.exception;

import com.pece.agencia.api.core.domain.Pacote;

public class PacoteForaValidadeException extends AbstractContratacaoPacoteException {
    public PacoteForaValidadeException(Pacote pacote) {
        super(String.format("Pacote com ID %s está fora do período de validade", pacote.getId()), pacote);
    }
}
