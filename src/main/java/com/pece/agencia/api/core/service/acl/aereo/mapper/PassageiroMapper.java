
package com.pece.agencia.api.core.service.acl.aereo.mapper;

import com.pece.agencia.api.aereo.Passageiro;
import com.pece.agencia.api.core.domain.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface PassageiroMapper {
    @Mapping(source = "nome", target = "nome")
    Passageiro toPassageiro(Cliente cliente);

}
