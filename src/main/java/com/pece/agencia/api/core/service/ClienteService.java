package com.pece.agencia.api.core.service;

import com.pece.agencia.api.core.domain.Cliente;
import com.pece.agencia.api.core.domain.Contratacao;
import com.pece.agencia.api.core.exception.ClienteNaoEncontradoException;
import com.pece.agencia.api.core.repository.ClienteRepository;
import com.pece.agencia.api.core.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ContratacaoRepository contratacaoRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ContratacaoRepository contratacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.contratacaoRepository = contratacaoRepository;
    }

    public Page<Contratacao> findAllCompras(UUID clientId, Pageable pageable) {
        return this.contratacaoRepository.findAllByCliente(clientId, pageable);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(UUID id) throws ClienteNaoEncontradoException {
        return clienteRepository.findById(id)
                                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }


    public Optional<Contratacao> findCompraById(UUID idCliente, UUID idCompra) {
        return this.contratacaoRepository.findById(idCompra)
                                         .filter(c -> c.getCliente().getId().equals(idCliente));
    }
}

