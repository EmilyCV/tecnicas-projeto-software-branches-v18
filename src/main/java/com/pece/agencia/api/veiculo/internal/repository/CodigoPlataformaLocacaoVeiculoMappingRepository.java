package com.pece.agencia.api.veiculo.internal.repository;

import com.pece.agencia.api.veiculo.internal.domain.CodigoPlataformaLocacaoVeiculoMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodigoPlataformaLocacaoVeiculoMappingRepository extends JpaRepository<CodigoPlataformaLocacaoVeiculoMapping, UUID> {
}
