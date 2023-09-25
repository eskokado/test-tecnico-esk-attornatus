package com.eskokado.attornatus.api.controllers;

import com.eskokado.attornatus.api.domain.Endereco;
import com.eskokado.attornatus.api.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;
    @PostMapping("/{pessoaId}")
    public ResponseEntity<Endereco> criarEnderecoParaPessoa(@PathVariable Long pessoaId, @RequestBody Endereco endereco) {
        Endereco enderecoCriado = enderecoService.criarEnderecoParaPessoa(pessoaId, endereco);
        if (enderecoCriado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoCriado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
