package com.example.cooperados.infrastructure.repository;

import com.example.cooperados.domain.model.Cooperado;
import com.example.cooperados.domain.port.CooperadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CooperadoRepositoryJpa implements CooperadoRepository {

    private final EntityManager em;

    @Override
    public Cooperado salvar(Cooperado cooperado) {
        em.persist(cooperado);
        return cooperado;
    }

    @Override
    public Optional<Cooperado> buscarPorId(Long id) {
        Cooperado c = em.find(Cooperado.class, id);
        if (c == null || !c.isAtivo()) return Optional.empty();
        return Optional.of(c);
    }

    @Override
    public Optional<Cooperado> buscarPorCpfCnpj(String cpfCnpj) {
        TypedQuery<Cooperado> query = em.createQuery(
            "SELECT c FROM Cooperado c WHERE c.cpfCnpj = :cpfCnpj AND c.ativo = true", Cooperado.class);
        query.setParameter("cpfCnpj", cpfCnpj);
        List<Cooperado> results = query.getResultList();
        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    @Override
    public List<Cooperado> listarTodos() {
        TypedQuery<Cooperado> query = em.createQuery(
            "SELECT c FROM Cooperado c WHERE c.ativo = true", Cooperado.class);
        return query.getResultList();
    }

    @Override
    public List<Cooperado> listarPorFiltro(String nome, String cpfCnpj) {
        StringBuilder jpql = new StringBuilder("SELECT c FROM Cooperado c WHERE c.ativo = true ");
        if (nome != null && !nome.isBlank()) {
            jpql.append("AND LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ");
        }
        if (cpfCnpj != null && !cpfCnpj.isBlank()) {
            jpql.append("AND c.cpfCnpj LIKE :cpfCnpj ");
        }
        TypedQuery<Cooperado> query = em.createQuery(jpql.toString(), Cooperado.class);
        if (nome != null && !nome.isBlank()) {
            query.setParameter("nome", nome);
        }
        if (cpfCnpj != null && !cpfCnpj.isBlank()) {
            query.setParameter("cpfCnpj", "%" + cpfCnpj + "%");
        }
        return query.getResultList();
    }

    @Override
    public Cooperado atualizar(Cooperado cooperado) {
        return em.merge(cooperado);
    }

    @Override
    public void remover(Cooperado cooperado) {
        cooperado.excluir();
        atualizar(cooperado);
    }
}
