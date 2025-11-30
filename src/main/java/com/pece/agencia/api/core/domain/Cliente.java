package com.pece.agencia.api.core.domain;

import com.pece.agencia.api.common.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CLIENTE")
@Data
public class Cliente {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONE")
    private String telefone;

    @Embedded
    @AttributeOverride(name = "cep", column = @Column(name = "CEP"))
    @AttributeOverride(name = "logradouro", column = @Column(name = "LOGRADOURO"))
    @AttributeOverride(name = "numero", column = @Column(name = "NUMERO"))
    @AttributeOverride(name = "complemento", column = @Column(name = "COMPLEMENTO"))
    @AttributeOverride(name = "bairro", column = @Column(name = "BAIRRO"))
    @AssociationOverride(name = "localidade", joinColumns =  @JoinColumn(name = "LOCALIDADE_ID"))
    private Endereco endereco;

    public Contratacao contratar(Pacote pacote, LocalDate inicioViagem) {
        Contratacao contratacao = new Contratacao(this, pacote, inicioViagem);
        return contratacao;
    }
}
