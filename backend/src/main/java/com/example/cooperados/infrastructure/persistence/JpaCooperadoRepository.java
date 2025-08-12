package com.cooperados.infrastructure.persistence;

import com.cooperados.domain.model.Cooperado;
import com.cooperados.domain.repository.CooperadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaCooperadoRepository implements CooperadoRepository {

    private final SpringDataCooperadoRepository repo;

    public JpaCooperadoRepository(SpringDataCooperadoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Cooperado save(Cooperado cooperado) {
        try {
            return repo.save(cooperado);
        } catch (DataIntegrityViolationException e) {
            throw e; // handled by ControllerAdvice to 409
        }
    }

    @Override
    public Optional<Cooperado> findById(UUID id) {
        return repo.findById(id).filter(Cooperado::isAtivo);
    }

    @Override
    public Optional<Cooperado> findByCpfCnpj(String cpfCnpj) {
        return repo.findByCpfCnpj(cpfCnpj);
    }

    @Override
    @Transactional
    public void deleteLogical(UUID id) {
        repo.findById(id).ifPresent(c -> {
            c.setAtivo(false);
            repo.save(c);
        });
    }

    @Override
    public List<Cooperado> search(String nome, String cpfCnpj) {
        String n = nome == null ? "" : nome;
        String c = cpfCnpj == null ? "" : cpfCnpj;
        return repo.findByAtivoTrueAndNomeContainingIgnoreCaseAndCpfCnpjContaining(n, c);
    }
}
