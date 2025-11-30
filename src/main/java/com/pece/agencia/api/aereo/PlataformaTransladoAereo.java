package com.pece.agencia.api.aereo;

import java.time.LocalDate;

public interface PlataformaTransladoAereo {
    ReservaVoo reservar(ReservaTransladoAereoRequest request) throws PlataformaTransladoAereoOfflineException;

    record ReservaTransladoAereoRequest(Passageiro passageiro, DadosVoo dadosVoo, LocalDate data) {
    }
}
