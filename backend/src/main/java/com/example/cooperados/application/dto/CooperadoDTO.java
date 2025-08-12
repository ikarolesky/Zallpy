package com.example.cooperados.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CooperadoDTO {

    private Long id;
    private String nome;
    private String cpfCnpj;
    private LocalDate dataNascimentoConstituicao;
    private BigDecimal rendaFaturamento;
    private String telefone;
    private String email;
}
