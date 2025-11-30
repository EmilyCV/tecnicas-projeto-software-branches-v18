package com.pece.agencia.api.hotelaria.internal.service;

import com.pece.agencia.api.hotelaria.Plataforma;
import com.pece.agencia.api.hotelaria.internal.domain.OfertaHospedagemPlataformaMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Component
public class PlataformaRegularReservaHospedagemServiceHandler extends AbstractPlataformaHospedagemServiceHandler {
    @Value("${plataforma.hotel.regular.url}")
    private String plataformaHotelRegularBaseUrl;

    public PlataformaRegularReservaHospedagemServiceHandler() {
        super(Plataforma.REGULAR);
    }

    @Override
    protected String doReservar(OfertaHospedagemPlataformaMapping mapping, ReservaHospedagemService.ReservaHospedagemRequest request) {
        Map<String, String> requestPayload = new HashMap<>();

        requestPayload.put("hospede", request.hospede().nome());
        requestPayload.put("data-check-in", request.periodo().inicio().toString());
        requestPayload.put("data-check-out", request.periodo().fim().toString());

        String idPlataforma = mapping.getCodigoHotel();

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaHotelRegularBaseUrl + "/api/v1/hoteis/" + idPlataforma + "/reservas", requestPayload, Map.class);
        return result.get("numero-reserva");
    }
}
