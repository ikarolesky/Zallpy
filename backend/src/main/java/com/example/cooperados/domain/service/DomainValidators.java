package com.cooperados.domain.service;

import com.cooperados.application.dto.CooperadoDTO;
import com.cooperados.domain.repository.CooperadoRepository;

import java.util.Optional;
import java.util.regex.Pattern;

public class DomainValidators {

    private static final Pattern NON_DIGITS = Pattern.compile("\\D");

    public static void validateForCreate(CooperadoDTO dto, CooperadoRepository repo) {
        if (dto.getNome() == null || dto.getNome().isBlank()) throw new IllegalArgumentException("Nome obrigatório");

        String raw = dto.getCpfCnpj();
        if (raw == null || raw.isBlank()) throw new IllegalArgumentException("CPF/CNPJ obrigatório");
        String digits = NON_DIGITS.matcher(raw).replaceAll("");

        if (isCpf(digits)) {
            dto.setTipo("CPF");
            if (dto.getDataNascimento() == null) throw new IllegalArgumentException("Data de nascimento obrigatória para CPF");
            if (dto.getRenda() == null) throw new IllegalArgumentException("Renda obrigatória para CPF");
        } else if (isCnpj(digits)) {
            dto.setTipo("CNPJ");
            if (dto.getDataConstituicao() == null) throw new IllegalArgumentException("Data de constituição obrigatória para CNPJ");
            if (dto.getFaturamento() == null) throw new IllegalArgumentException("Faturamento obrigatório para CNPJ");
        } else {
            throw new IllegalArgumentException("CPF ou CNPJ inválido");
        }

        if (!isValidPhone(dto.getTelefone())) throw new IllegalArgumentException("Telefone inválido");

        // Unicidade (verifica qualquer registro mesmo inativo)
        Optional.ofNullable(repo.findByCpfCnpj(digits)).ifPresent(existing -> {
            if (existing.isPresent()) throw new IllegalArgumentException("Já existe um cooperado com este CPF/CNPJ");
        });

        dto.setCpfCnpj(digits);
    }

    public static void validateForUpdate(CooperadoDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) throw new IllegalArgumentException("Nome obrigatório");
        if (!isValidPhone(dto.getTelefone())) throw new IllegalArgumentException("Telefone inválido");
    }

    private static boolean isValidPhone(String telefone) {
        if (telefone == null) return false;
        String d = NON_DIGITS.matcher(telefone).replaceAll("");
        return d.length() == 10 || d.length() == 11;
    }

    // CPF validator
    public static boolean isCpf(String s) {
        if (s == null) return false;
        String n = s.replaceAll("\\D", "");
        if (n.length() != 11) return false;
        if (n.matches("^(\\d)\\1{10}$")) return false;
        try {
            int[] nums = n.chars().map(c -> c - '0').toArray();
            int sum = 0;
            for (int i = 0; i < 9; i++) sum += nums[i] * (10 - i);
            int r = sum % 11;
            int dig1 = (r < 2) ? 0 : 11 - r;
            if (dig1 != nums[9]) return false;
            sum = 0;
            for (int i = 0; i < 10; i++) sum += nums[i] * (11 - i);
            r = sum % 11;
            int dig2 = (r < 2) ? 0 : 11 - r;
            return dig2 == nums[10];
        } catch (Exception ex) {
            return false;
        }
    }

    // CNPJ validator
    public static boolean isCnpj(String s) {
        if (s == null) return false;
        String n = s.replaceAll("\\D", "");
        if (n.length() != 14) return false;
        if (n.matches("^(\\d)\\1{13}$")) return false;
        try {
            int[] nums = n.chars().map(c -> c - '0').toArray();
            int[] weight1 = {5,4,3,2,9,8,7,6,5,4,3,2};
            int sum = 0;
            for (int i = 0; i < 12; i++) sum += nums[i] * weight1[i];
            int r = sum % 11;
            int dig1 = (r < 2) ? 0 : 11 - r;
            if (dig1 != nums[12]) return false;
            int[] weight2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};
            sum = 0;
            for (int i = 0; i < 13; i++) sum += nums[i] * weight2[i];
            r = sum % 11;
            int dig2 = (r < 2) ? 0 : 11 - r;
            return dig2 == nums[13];
        } catch (Exception ex) {
            return false;
        }
    }
}
