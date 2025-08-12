package com.example.cooperados.domain.port;

import com.example.cooperados.domain.model.Cooperado;

import java.util.List;
import java.util.Optional;

public interface CooperadoRepository {

    Cooperado salvar(Cooperado cooperado);

    Optional<Cooperado> buscarPorId(Long id);

    Optional<Cooperado> buscarPorCpfCnpj(String cpfCnpj);

    List<Cooperado> listarTodos();

    List<Cooperado> listarPorFiltro(String nome, String cpfCnpj);

    Cooperado atualizar(Cooperado cooperado);

    void remover(Cooperado cooperado);
}
