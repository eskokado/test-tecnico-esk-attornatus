package com.eskokado.attornatus.api.controllers;

import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;
    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        Pessoa pessoaCriada = pessoaService.criarPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> editarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Pessoa pessoaEditada = pessoaService.editarPessoa(id, pessoa);
        if (pessoaEditada != null) {
            return ResponseEntity.ok(pessoaEditada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> consultarPessoa(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.consultarPessoa(id);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
