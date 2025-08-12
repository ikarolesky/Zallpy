package com.example.cooperados.application.service;

import com.example.cooperados.application.dto.CooperadoDTO;
import com.example.cooperados.application.mapper.CooperadoMapper;
import com.example.cooperados.domain.exception.CooperadoException;
import com.example.cooperados.domain.model.Cooperado;
import com.example.cooperados.domain.port.CooperadoRepository;
import com.example.cooperados.domain.validation.CpfCnpjValidator;
import com.example.cooperados.domain.validation.TelefoneValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CooperadoService {

    private final CooperadoRepository repository;

    public CooperadoDTO criarCooperado(CooperadoDTO dto) {
        validarCooperado(dto, true);
        var entity = CooperadoMapper.toEntity(dto);
        var salvo = repository.salvar(entity);
        return CooperadoMapper.toDTO(salvo);
    }

    public CooperadoDTO atualizarCooperado(Long id, CooperadoDTO dto) {
        var opt = repository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new CooperadoException("Cooperado não encontrado");
        }
        var existente = opt.get();

        // CPF/CNPJ não pode ser alterado
        dto.setCpfCnpj(existente.getCpfCnpj());

        validarCooperado(dto, false);

        existente.setNome(dto.getNome());
        existente.setDataNascimentoConstituicao(dto.getDataNascimentoConstituicao());
        existente.setRendaFaturamento(dto.getRendaFaturamento());
        existente.setTelefone(dto.getTelefone());
        existente.setEmail(dto.getEmail());

        var atualizado = repository.atualizar(existente);
        return CooperadoMapper.toDTO(atualizado);
    }

    public CooperadoDTO buscarPorId(Long id) {
        var opt = repository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new CooperadoException("Cooperado não encontrado");
        }
        return CooperadoMapper.toDTO(opt.get());
    }

    public List<CooperadoDTO> listarTodos(String nome, String cpfCnpj) {
        var lista = repository.listarPorFiltro(nome, cpfCnpj);
        return lista.stream()
                .map(CooperadoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void remover(Long id) {
        var opt = repository.buscarPorId(id);
        if (opt.isEmpty()) {
            throw new CooperadoException("Cooperado não encontrado");
        }
        repository.remover(opt.get());
    }

    private void validarCooperado(CooperadoDTO dto, boolean isNew) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new CooperadoException("Nome é obrigatório");
        }
        if (dto.getCpfCnpj() == null || !CpfCnpjValidator.isCpfOrCnpjValid(dto.getCpfCnpj())) {
            throw new CooperadoException("CPF ou CNPJ inválido");
        }
        if (dto.getDataNascimentoConstituicao() == null) {
            throw new CooperadoException("Data de nascimento ou constituição é obrigatória");
        }
        if (dto.getRendaFaturamento() == null || dto.getRendaFaturamento().doubleValue() <= 0) {
            throw new CooperadoException("Renda ou faturamento deve ser maior que zero");
        }
        if (dto.getTelefone() == null || !TelefoneValidator.isValidTelefone(dto.getTelefone())) {
            throw new CooperadoException("Telefone inválido");
        }

        if (isNew) {
            var existe = repository.buscarPorCpfCnpj(dto.getCpfCnpj());
            if (existe.isPresent()) {
                throw new CooperadoException("CPF ou CNPJ já cadastrado");
            }
        }
    }
}
