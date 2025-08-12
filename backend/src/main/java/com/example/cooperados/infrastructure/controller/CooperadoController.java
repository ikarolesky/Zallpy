package com.example.cooperados.infrastructure.controller;

import com.example.cooperados.application.dto.CooperadoDTO;
import com.example.cooperados.application.service.CooperadoService;
import com.example.cooperados.domain.exception.CooperadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cooperados")
@RequiredArgsConstructor
public class CooperadoController {

    private final CooperadoService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CooperadoDTO dto) {
        try {
            var criado = service.criarCooperado(dto);
            return ResponseEntity.ok(criado);
        } catch (CooperadoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CooperadoDTO dto) {
        try {
            var atualizado = service.atualizarCooperado(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (CooperadoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            var dto = service.buscarPorId(id);
            return ResponseEntity.ok(dto);
        } catch (CooperadoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CooperadoDTO>> listarTodos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpfCnpj) {
        var lista = service.listarTodos(nome, cpfCnpj);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            service.remover(id);
            return ResponseEntity.noContent().build();
        } catch (CooperadoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
