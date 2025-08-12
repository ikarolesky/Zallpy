package com.cooperados.infrastructure.persistence;

import com.cooperados.domain.model.Cooperado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataCooperadoRepository extends JpaRepository<Cooperado, UUID> {
    Optional<Cooperado> findByCpfCnpj(String cpfCnpj);
    List<Cooperado> findByAtivoTrueAndNomeContainingIgnoreCaseAndCpfCnpjContaining(String nome, String cpfCnpj);
}
