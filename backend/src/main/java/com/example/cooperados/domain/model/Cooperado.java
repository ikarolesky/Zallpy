package com.cooperados.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cooperado", indexes = {@Index(name = "idx_cpfcnpj", columnList = "cpf_cnpj")})
public class Cooperado {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 20)
    private String cpfCnpj; // normalized (digits only)

    @Column(nullable = false, length = 10)
    private String tipo; // "CPF" or "CNPJ"

    private LocalDate dataNascimento; // for CPF
    private LocalDate dataConstituicao; // for CNPJ

    @Column(precision = 15, scale = 2)
    private BigDecimal renda; // for CPF

    @Column(precision = 15, scale = 2)
    private BigDecimal faturamento; // for CNPJ

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(length = 255)
    private String email;

    @Column(nullable = false)
    private boolean ativo = true;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}
