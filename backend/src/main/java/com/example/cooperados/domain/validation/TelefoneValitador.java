package com.example.cooperados.domain.validation;

public class TelefoneValidator {

    public static boolean isValidTelefone(String telefone) {
        if (telefone == null) return false;

        // Remove caracteres não numéricos
        String num = telefone.replaceAll("\\D", "");
        // Verifica o tamanho para celular ou fixo com DDD
        if (num.length() < 10 || num.length() > 11) return false;

        // Regex para validar formato de telefone BR (DDD + 8 ou 9 números)
        return num.matches("^\\d{10,11}$");
    }
}
