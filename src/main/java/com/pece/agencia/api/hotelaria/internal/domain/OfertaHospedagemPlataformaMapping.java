package com.pece.agencia.api.hotelaria.internal.domain;

import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import com.pece.agencia.api.hotelaria.Plataforma;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Table(name = "OFERTA_HOSPEDAGEM_PLATAFORMA_MAPPING")
@Data
@Entity
public class OfertaHospedagemPlataformaMapping {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "PLATAFORMA")
    @Enumerated(EnumType.STRING)
    private Plataforma plataforma;

    @Column(name = "CODIGO_HOTEL")
    private String codigoHotel;

    @Column(name = "CODIGO_PROMOCAO")
    private String codigoPromocao;
}
