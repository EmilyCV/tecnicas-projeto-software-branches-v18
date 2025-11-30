package com.pece.agencia.api.core.service.acl.pagamento.mapper;


import com.pece.agencia.api.pagamento.DadosCartao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface DadosCartaoMapper {
    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "cvc", target = "cvc")
    @Mapping(source = "dataExpiracao", target = "dataExpiracao")
    DadosCartao toDadosCartao(com.pece.agencia.api.core.domain.DadosCartao dadosCartao);
}
