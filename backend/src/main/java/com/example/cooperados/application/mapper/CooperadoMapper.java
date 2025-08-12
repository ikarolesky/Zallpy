package com.example.cooperados.application.mapper;

import com.example.cooperados.application.dto.CooperadoDTO;
import com.example.cooperados.domain.model.Cooperado;

public class CooperadoMapper {

    public static Cooperado toEntity(CooperadoDTO dto) {
        Cooperado c = new Cooperado();
        c.setId(dto.getId());
        c.setNome(dto.getNome());
        c.setCpfCnpj(dto.getCpfCnpj());
        c.setDataNascimentoConstituicao(dto.getDataNascimentoConstituicao());
        c.setRendaFaturamento(dto.getRendaFaturamento());
        c.setTelefone(dto.getTelefone());
        c.setEmail(dto.getEmail());
        c.setAtivo(true);
        return c;
    }

    public static CooperadoDTO toDTO(Cooperado entity) {
        return new CooperadoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getDataNascimentoConstituicao(),
                entity.getRendaFaturamento(),
                entity.getTelefone(),
                entity.getEmail());
    }
}
