package com.cooperados.domain.repository;

import com.cooperados.domain.model.Cooperado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CooperadoRepository {
    Cooperado save(Cooperado cooperado);
    Optional<Cooperado> findById(UUID id);
    Optional<Cooperado> findByCpfCnpj(String cpfCnpj);
    void deleteLogical(UUID id);
    List<Cooperado> search(String nome, String cpfCnpj);
}
