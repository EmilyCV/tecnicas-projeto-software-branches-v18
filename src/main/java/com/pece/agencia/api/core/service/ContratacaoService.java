package com.pece.agencia.api.core.service;

import com.pece.agencia.api.core.domain.*;
import com.pece.agencia.api.core.exception.*;
import com.pece.agencia.api.core.repository.ContratacaoRepository;
import com.pece.agencia.api.core.service.acl.aereo.ReservaTransladoAereoServiceAdapter;
import com.pece.agencia.api.core.service.acl.hotelaria.ReservaHospedagemServiceAdapter;
import com.pece.agencia.api.core.service.acl.pagamento.GatewayPagamentoAdapter;
import com.pece.agencia.api.core.service.acl.veiculo.LocacaoVeiculoServiceAdapter;
import jakarta.validation.constraints.Future;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Validated
public class ContratacaoService {
    private final ContratacaoRepository contratacaoRepository;
    private final PacoteService pacoteService;
    private final ClienteService clienteService;
    private final GatewayPagamentoAdapter gatewayPagamento;
    private final ReservaHospedagemServiceAdapter reservaHospedagemService;
    private final LocacaoVeiculoServiceAdapter locacaoVeiculoService;
    private final ReservaTransladoAereoServiceAdapter reservaTransladoAereoService;

    public ContratacaoService(ContratacaoRepository contratacaoRepository, PacoteService pacoteService, ClienteService clienteService, GatewayPagamentoAdapter gatewayPagamento, ReservaHospedagemServiceAdapter reservaHospedagemService, LocacaoVeiculoServiceAdapter locacaoVeiculoService, ReservaTransladoAereoServiceAdapter reservaTransladoAereoService) {
        this.contratacaoRepository = contratacaoRepository;
        this.pacoteService = pacoteService;
        this.clienteService = clienteService;
        this.gatewayPagamento = gatewayPagamento;
        this.reservaHospedagemService = reservaHospedagemService;
        this.locacaoVeiculoService = locacaoVeiculoService;
        this.reservaTransladoAereoService = reservaTransladoAereoService;
    }

    public Contratacao findById(UUID id) throws ContratacaoNaoEncontradaException {
        return this.contratacaoRepository.findById(id)
                                         .orElseThrow(() -> new ContratacaoNaoEncontradaException(id));
    }

    @Transactional(rollbackFor = FalhaCobrancaException.class)
    public Contratacao contratar(UUID pacoteId, UUID clientId, DadosCartao dadosCartao, @Future LocalDate dataIda) throws FalhaCobrancaException, PlataformaParceiroOfflineException, PacoteNaoEncontradoException, PacoteForaValidadeException, PacoteIndisponivelException, ClienteNaoEncontradoException {
        Cliente cliente = this.clienteService.findById(clientId);
        Pacote pacote = this.pacoteService.findById(pacoteId);

        OfertaTransladoAereo ofertaTransladoAereo = pacote.ofertaDoTipo(OfertaTransladoAereo.class);

        Contratacao contratacao = cliente.contratar(pacote, dataIda);
        validar(contratacao);

        contratacao.setCodigoPagamento(this.gatewayPagamento.pagar(dadosCartao, contratacao.getValorPago()));
        contratacao.setReservaHotel(this.reservaHospedagemService.reservar(contratacao.getPacoteContratado().ofertaDoTipo(OfertaHospedagem.class), cliente, contratacao.getPeriodoViagem()));
        contratacao.setLocalizadorReservaVeiculo(this.locacaoVeiculoService.locar(contratacao.getPacoteContratado().ofertaDoTipo(OfertaLocacaoVeiculo.class), cliente, pacote.getDestino(), contratacao.getPeriodoViagem()));

        contratacao.setReservaVooIda(this.reservaTransladoAereoService.reservar(contratacao.getCliente(), ofertaTransladoAereo.getVooIda(), contratacao.getPeriodoViagem().inicio()));
        contratacao.setReservaVooVolta(this.reservaTransladoAereoService.reservar(contratacao.getCliente(), ofertaTransladoAereo.getVooVolta(), contratacao.getPeriodoViagem().fim()));

        this.contratacaoRepository.save(contratacao);

        return contratacao;
    }

    private void validar(Contratacao contratacao) throws PacoteForaValidadeException, PacoteIndisponivelException {
        contratacao.getPacoteContratado().garantirDisponibilidade();

        if (contratacao.getPacoteContratado().expiradoEm(contratacao.getMomentoCompra())) {
            throw new PacoteForaValidadeException(contratacao.getPacoteContratado());
        }
    }
}
