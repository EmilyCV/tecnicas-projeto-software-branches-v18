package com.pece.agencia.api.core.controller.v1.mapper;

import com.pece.agencia.api.core.domain.Localidade;
import com.pece.agencia.api.core.service.acl.veiculo.LocacaoVeiculoServiceAdapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LocalidadeMapper {
    @Autowired
    private LocacaoVeiculoServiceAdapter locacaoVeiculoServiceAdapter;

    @Mapping(source = ".", target = "codigoLocadoraVeiculo")
    public abstract com.pece.agencia.api.core.controller.v1.dto.Localidade toDTO(Localidade entity);

    protected String toCodigoPlataformaLocacaoVeiculo(Localidade localidade) {
        return locacaoVeiculoServiceAdapter.obterCodigoPlataformaLocacaoVeiculo(localidade);
    }
}