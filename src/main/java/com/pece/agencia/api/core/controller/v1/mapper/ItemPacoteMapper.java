package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.controller.v1.dto.AluguelCarro;
import com.pece.agencia.api.core.controller.v1.dto.ReservaHotel;
import com.pece.agencia.api.core.controller.v1.dto.TransladoAereoItem;
import com.pece.agencia.api.core.domain.Oferta;
import com.pece.agencia.api.core.domain.OfertaHospedagem;
import com.pece.agencia.api.core.domain.OfertaLocacaoVeiculo;
import com.pece.agencia.api.core.domain.OfertaTransladoAereo;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(
    componentModel = "spring",
    subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
    uses = {
        AluguelCarroMapper.class,
        TransladoAereoItemMapper.class,
        ReservaHotelMapper.class
    }
)
public interface ItemPacoteMapper {
    @SubclassMapping(source = OfertaLocacaoVeiculo.class, target = AluguelCarro.class)
    @SubclassMapping(source = OfertaTransladoAereo.class, target = TransladoAereoItem.class)
    @SubclassMapping(source = OfertaHospedagem.class, target = ReservaHotel.class)
    com.pece.agencia.api.core.controller.v1.dto.PacoteItemsInner toDTO(Oferta<?> entity);

}
