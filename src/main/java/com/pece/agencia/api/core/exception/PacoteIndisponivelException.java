package com.pece.agencia.api.core.exception;

import com.pece.agencia.api.core.domain.Pacote;
import lombok.Getter;

@Getter
public class PacoteIndisponivelException extends AbstractContratacaoPacoteException {
    public PacoteIndisponivelException(Pacote pacote) {
        super(String.format("Pacote com ID %s está indisponível", pacote.getId()), pacote);
    }
}
