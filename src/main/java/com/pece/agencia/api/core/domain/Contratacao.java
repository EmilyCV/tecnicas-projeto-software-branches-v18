package com.pece.agencia.api.core.domain;

import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CONTRATACAO")
@Data
public class Contratacao {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name="CLIENTE_ID")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="PACOTE_CONTRATADO_ID")
    private Pacote pacoteContratado;

    @Embedded
    @AttributeOverride(name = "inicio", column = @Column(name = "INICIO_PERIODO_VIAGEM"))
    @AttributeOverride(name = "fim", column = @Column(name = "FIM_PERIODO_VIAGEM"))
    private Periodo periodoViagem;

    @Column(name = "MOMENTO_COMPRA")
    private LocalDate momentoCompra;
    @Column(name = "VALOR_TOTAL")
    private double valorTotal;
    @Column(name = "VALOR_DESCONTO")
    private double valorDesconto;
    @Column(name = "VALOR_PAGO")
    private double valorPago;
    @Column(name = "CODIGO_PAGAMENTO")
    private String codigoPagamento;
    @Column(name = "RESERVA_HOTEL")
    private String reservaHotel;

    @Embedded
    @AttributeOverride(name = "eticket", column = @Column(name = "ETICKET_RESERVA_IDA"))
    @AttributeOverride(name = "assento", column = @Column(name = "ASSENTO_RESERVA_IDA"))
    @AttributeOverride(name = "horarioEmbarque", column = @Column(name = "HORARIO_EMBARQUE_RESERVA_IDA"))
    @AttributeOverride(name = "dadosVoo.numero", column = @Column(name = "NUMERO_VOO_RESERVA_IDA"))
    @AttributeOverride(name = "dadosVoo.horario", column = @Column(name = "HORARIO_VOO_RESERVA_IDA"))
    private ReservaVoo reservaVooIda;

    @Embedded
    @AttributeOverride(name = "eticket", column = @Column(name = "ETICKET_RESERVA_VOLTA"))
    @AttributeOverride(name = "assento", column = @Column(name = "ASSENTO_RESERVA_VOLTA"))
    @AttributeOverride(name = "horarioEmbarque", column = @Column(name = "HORARIO_EMBARQUE_RESERVA_VOLTA"))
    @AttributeOverride(name = "dadosVoo.numero", column = @Column(name = "NUMERO_VOO_RESERVA_VOLTA"))
    @AttributeOverride(name = "dadosVoo.horario", column = @Column(name = "HORARIO_VOO_RESERVA_VOLTA"))
    private ReservaVoo reservaVooVolta;

    @Column(name = "LOCALIZADOR_RESERVA_VEICULO")
    private String localizadorReservaVeiculo;

    protected Contratacao() {
        // Para o hibernate
    }
    public Contratacao(Cliente cliente, Pacote pacoteContratado, LocalDate inicioViagem) {
        this.cliente = cliente;
        this.pacoteContratado = pacoteContratado;
        this.momentoCompra = LocalDate.now();
        this.valorPago = pacoteContratado.getValorTotalAPagar();
        this.valorDesconto = pacoteContratado.getValorDescontoPromocional();
        this.valorTotal = pacoteContratado.getPrecoBase();
        this.periodoViagem = this.pacoteContratado.periodoViagemIniciandoEm(inicioViagem);
    }
}
